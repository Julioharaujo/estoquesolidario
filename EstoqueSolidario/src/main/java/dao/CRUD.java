package dao;

import java.util.List;

public interface CRUD <T, ID>{
    T pesquisaPeloId(ID id);
    List<T> lista();
    void insere(T t);
    void atualiza(T t);
    void remove(T t);
}
