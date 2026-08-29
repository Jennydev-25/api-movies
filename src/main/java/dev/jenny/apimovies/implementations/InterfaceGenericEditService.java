package dev.jenny.apimovies.implementations;

public interface InterfaceGenericEditService<T, S> {

    S storeEntity(T dto);

    S updateEntity(Long id, T dto);

}