package com.kkb.mvc;

import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class HandlerMapping {
    private static Map<String, MVCMapping> data = new HashMap<>();

    public static MVCMapping get(String uri){
        return data.get(uri);
    }
    public static void load(InputStream is) {
        Properties ppt = new Properties();
        try {
            ppt.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Collection<Object> values = ppt.values();
        for (Object object : values) {
            String className = (String) object;
            try {
                Class c = Class.forName(className);
                Object o = c.getConstructor().newInstance();
                Method[] methods = c.getMethods();
                for (Method m : methods) {
                    Annotation[] as = m.getAnnotations();
                    if (as != null) {
                        for (Annotation annotation : as) {
                            if (annotation instanceof ResponseBody) {
                                MVCMapping mvcMapping = new MVCMapping(o,m,ResponseType.TEXT);
                                Object obj = data.put(((ResponseBody) annotation).value(), mvcMapping);
                                if(obj!=null){
                                    throw new RuntimeException("duplicate request"+((ResponseBody) annotation).value());
                                }
                            } else if (annotation instanceof ResponseView) {
                                MVCMapping mvcMapping = new MVCMapping(o,m,ResponseType.VIEW);
                                Object obj = data.put(((ResponseView) annotation).value(), mvcMapping);
                                if(obj!=null){
                                    throw new RuntimeException("duplicate request"+((ResponseView) annotation).value());
                                }
                            }
                        }
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public static class MVCMapping {
        private Object obj;
        private Method method;
        private ResponseType responseType;

        public MVCMapping() {
        }

        public Object getObj() {
            return obj;
        }

        public void setObj(Object obj) {
            this.obj = obj;
        }

        public Method getMethod() {
            return method;
        }

        public void setMethod(Method method) {
            this.method = method;
        }

        public ResponseType getResponseType() {
            return responseType;
        }

        public void setResponseType(ResponseType responseType) {
            this.responseType = responseType;
        }

        public MVCMapping(Object obj, Method method, ResponseType responseType) {
            this.obj = obj;
            this.method = method;
            this.responseType = responseType;
        }
    }
}
