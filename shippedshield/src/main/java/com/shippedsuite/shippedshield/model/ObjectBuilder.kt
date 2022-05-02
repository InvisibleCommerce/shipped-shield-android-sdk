package com.shippedsuite.shippedshield.model

interface ObjectBuilder<ObjectType> {
    fun build(): ObjectType
}