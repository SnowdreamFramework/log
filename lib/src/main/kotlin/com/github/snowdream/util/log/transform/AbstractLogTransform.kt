package com.github.snowdream.util.log.transform

/**
 *
 *
 * Created by snowdream on 17/4/29.
 */
abstract class AbstractLogTransform {
    /**
     * transform the obj to string.
     *
     * @param obj
     *
     * @return
     */
    abstract fun transform(obj: Any): String
}