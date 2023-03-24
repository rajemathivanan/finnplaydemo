package com.finnplay.demo.app.utils;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

public final class AppUserUtils {

  private AppUserUtils() {
    
  }
  public static boolean patternMatches(String inpuString, String regexPattern) {
    return Pattern.compile(regexPattern)
        .matcher(inpuString)
        .matches();
  }

  public static void copyNonNullProperties(Object source, Object destination) {
    BeanUtils.copyProperties(source, destination,
        getNullPropertyNames(source));
  }

  /**
   * Returns an array of null properties of an object
   * 
   * @param source
   * @return
   */
  private static String[] getNullPropertyNames(Object source) {
    final BeanWrapper src = new BeanWrapperImpl(source);
    java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();
    Set emptyNames = new HashSet();
    for (java.beans.PropertyDescriptor pd : pds) {
      // check if value of this property is null then add it to the collection
      Object srcValue = src.getPropertyValue(pd.getName());
      if (srcValue == null || srcValue.equals(""))
        emptyNames.add(pd.getName());
    }
    String[] result = new String[emptyNames.size()];
    return (String[]) emptyNames.toArray(result);
  }
}
