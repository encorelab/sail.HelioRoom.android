package org.encorelab.sail;

/**
 * TODO Description
 *
 * @author Gugo
 */
public class StringUtilities {
        
        /**
           Transforms special characters in characters understandable for java
           
           <P>The following characters are replaced with corresponding character entities :
           <table border='1' cellpadding='3' cellspacing='0'>
           <tr><th> Character </th><th> Encoding </th></tr>
           <tr><td> < </td><td> &lt; </td></tr>
           <tr><td> > </td><td> &gt; </td></tr>
           <tr><td> & </td><td> &amp; </td></tr>
           <tr><td> " </td><td> &quot;</td></tr>
           <tr><td> ' </td><td> &apos;</td></tr>
           </table>
          */
          public static String toJava(String text) {
                  return text.replace("&lt;","<").replace("&gt;", ">").replace("&quot;", "\"").replace("&apos;", "\'").replace("&amp;", "&");
          }
          
          
          public static String toXMl(String text) {
                  return null;
          }
          
          
          public static  String capitalize (String s) {
                        if (s.length() == 0) 
                                return s;
                        return s.substring(0, 1).toUpperCase() + s.substring(1);
                }

}
