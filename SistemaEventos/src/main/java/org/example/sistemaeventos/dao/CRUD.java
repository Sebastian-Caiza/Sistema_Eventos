package org.example.sistemaeventos.dao;

import java.util.List;

public interface CRUD<T> {

    void guardar(T objeto);

    void actualizar(T objeto);

    void eliminar(int id);

    List<T> listar();
}