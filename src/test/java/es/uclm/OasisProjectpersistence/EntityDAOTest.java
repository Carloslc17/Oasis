package es.uclm.OasisProjectpersistence;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import es.uclm.OasisProject.persistence.EntityDAO;
import es.uclm.OasisProject.persistence.GestorBD;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class EntityDAOTest {
	
	    static class TestEntity {}

	    static class TestDAO extends EntityDAO<TestEntity> {
	        public TestDAO() {
	            super(TestEntity.class);
	        }
	    }

	    @InjectMocks
	    private TestDAO dao;

	    @Mock
	    private GestorBD gestorBD;

	    @BeforeEach
	    void setUp() {
	        MockitoAnnotations.openMocks(this);
	    }

	    @Test
	    void select_ok() {
	        TestEntity entidad = new TestEntity();

	        when(gestorBD.select(TestEntity.class, 1)).thenReturn(entidad);

	        TestEntity result = dao.select(1);

	        assertEquals(entidad, result);
	    }

	    @Test
	    void selectAll_ok() {
	        when(gestorBD.selectAll(TestEntity.class))
	                .thenReturn(List.of(new TestEntity()));

	        List<TestEntity> result = dao.selectAll();

	        assertEquals(1, result.size());
	    }

	    @Test
	    void insert_ok() {
	        TestEntity entidad = new TestEntity();

	        dao.insert(entidad);

	        verify(gestorBD).insert(entidad);
	    }

	    @Test
	    void update_ok() {
	        TestEntity entidad = new TestEntity();

	        when(gestorBD.update(entidad)).thenReturn(entidad);

	        TestEntity result = dao.update(entidad);

	        assertEquals(entidad, result);
	    }

	    @Test
	    void delete_ok() {
	        TestEntity entidad = new TestEntity();

	        dao.delete(entidad);

	        verify(gestorBD).delete(entidad);
	    }

	    @Test
	    void deleteById_ok() {
	        dao.deleteById(1);

	        verify(gestorBD).deleteById(TestEntity.class, 1);
	    }


}
