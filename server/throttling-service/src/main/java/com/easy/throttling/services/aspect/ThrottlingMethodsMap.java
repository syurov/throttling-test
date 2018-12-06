package com.easy.throttling.services.aspect;

import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by syurov on 12/5/2018.
 */
public class ThrottlingMethodsMap extends ConcurrentHashMap<String, LinkedList<Long>> {
}
