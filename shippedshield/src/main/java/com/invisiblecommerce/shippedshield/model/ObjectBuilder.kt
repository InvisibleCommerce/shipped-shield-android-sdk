package com.invisiblecommerce.shippedshield.model

interface ObjectBuilder<ObjectType> {
    fun build(): ObjectType
}
