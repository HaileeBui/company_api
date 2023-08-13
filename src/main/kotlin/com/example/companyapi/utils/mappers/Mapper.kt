package com.example.companyapi.utils.mappers

interface Mapper<D, E> {
    fun fromEntity(entity: E) : D
    fun toEntity(domain: D): E
}