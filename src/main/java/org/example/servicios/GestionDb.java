package org.example.servicios;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Root;
import org.example.Main;
import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaQuery;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

public class GestionDb<T> {

        private static EntityManagerFactory emf;
        private Class<T> claseEntidad;
    public GestionDb(Class<T> claseEntidad) {
        if(emf == null) {
            if(Main.getModoConexion().equalsIgnoreCase("Heroku")){
                emf = getConfiguracionBaseDatosHeroku();
            }else{
                emf = Persistence.createEntityManagerFactory("MiUnidadPersistencia");
            }
        }
        this.claseEntidad = claseEntidad;

    }

    public EntityManager getEntityManager(){
        return emf.createEntityManager();
    }

    private EntityManagerFactory getConfiguracionBaseDatosHeroku(){
        //Leyendo la información de la variable de ambiente de Heroku
        String databaseUrl = System.getenv("DATABASE_URL");
        StringTokenizer st = new StringTokenizer(databaseUrl, ":@/");
        //Separando las información del conexión.
        String dbVendor = st.nextToken();
        String userName = st.nextToken();
        String password = st.nextToken();
        String host = st.nextToken();
        String port = st.nextToken();
        String databaseName = st.nextToken();
        //creando la jbdc String
        String jdbcUrl = String.format("jdbc:postgresql://%s:%s/%s?sslmode=require", host, port, databaseName);
        //pasando las propiedades.
        Map<String, String> properties = new HashMap<>();
        properties.put("javax.persistence.jdbc.url", jdbcUrl );
        properties.put("javax.persistence.jdbc.user", userName );
        properties.put("javax.persistence.jdbc.password", password );
        //
        return Persistence.createEntityManagerFactory("Heroku", properties);
    }

    private Object getValorCampo(T entidad){
        if(entidad == null){
            return null;
        }
        //aplicando la clase de reflexión.
        for(Field f : entidad.getClass().getDeclaredFields()) {  //tomando todos los campos privados.
            if (f.isAnnotationPresent(Id.class)) { //preguntando por la anotación ID.
                try {
                    f.setAccessible(true);
                    Object valorCampo = f.get(entidad);

                    System.out.println("Nombre del campo: "+f.getName());
                    System.out.println("Tipo del campo: "+f.getType().getName());
                    System.out.println("Valor del campo: "+valorCampo );

                    return valorCampo;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return null;
    }
    public T crear(T entidad) throws IllegalArgumentException, EntityExistsException, PersistenceException{
        EntityManager em = getEntityManager();

        try {

            em.getTransaction().begin();
            em.persist(entidad);
            em.getTransaction().commit();

        }finally {
            em.close();
        }
        return entidad;
    }
    public T editar(T entidad) throws PersistenceException{
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            em.merge(entidad);
            em.getTransaction().commit();
        }finally {
            em.close();
        }
        return entidad;
    }
    public boolean eliminar(Object entidadId) throws PersistenceException{
        boolean ok = false;
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            T entidad = em.find(claseEntidad, entidadId);
            em.remove(entidad);
            em.getTransaction().commit();
            ok = true;
        }finally {
            em.close();
        }
        return ok;
    }
    public T find(Object id) throws PersistenceException {
        EntityManager em = getEntityManager();
        try{
            return em.find(claseEntidad, id);
        } finally {
            em.close();
        }
    }
    public List<T> findAll() throws PersistenceException {
        EntityManager em = getEntityManager();
        try{
            CriteriaQuery<T> criteriaQuery = em.getCriteriaBuilder().createQuery(claseEntidad);
            criteriaQuery.select(criteriaQuery.from(claseEntidad));
            return em.createQuery(criteriaQuery).getResultList();
        } finally {
            em.close();
        }
    }
    public T verificarCredenciales(String username, String password) throws PersistenceException {
        EntityManager em = getEntityManager();

        try {
            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(claseEntidad);
            Root<T> root = criteriaQuery.from(claseEntidad);
            criteriaQuery.select(root);
            criteriaQuery.where(
                    criteriaBuilder.and(
                            criteriaBuilder.equal(root.get("username"), username),
                            criteriaBuilder.equal(root.get("password"), password)
                    )
            );

            TypedQuery<T> typedQuery = em.createQuery(criteriaQuery);
            typedQuery.setMaxResults(1);

            List<T> resultado = typedQuery.getResultList();

            if (!resultado.isEmpty()) {
                return resultado.get(0);
            } else {
                return null;
            }
        } finally {
            em.close();
        }
    }
}
