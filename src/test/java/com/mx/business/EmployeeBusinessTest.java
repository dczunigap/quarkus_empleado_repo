package test.java.com.mx.business;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.mx.business.EmpleadoBusiness;
import com.mx.entity.Empleado;
import com.mx.repository.EmpleadoRepository;

import io.quarkus.hibernate.orm.panache.PanacheQuery;

import java.lang.reflect.Field;

public class EmployeeBusinessTest {

    private EmployeeBusiness employeeBusiness;
    private EmployeeRepository employeeRepository;

    @BeforeEach
    public void setup() throws Exception {
        employeeBusiness = new EmployeeBusiness();
        employeeRepository = Mockito.mock(EmployeeRepository.class);

        // inject mock repository via reflection
        Field repoField = EmployeeBusiness.class.getDeclaredField("employeeRepository");
        repoField.setAccessible(true);
        repoField.set(employeeBusiness, employeeRepository);
    }

    @Test
    public void testGetAllEmpleados() {
        EmployeeEntity e1 = new EmployeeEntity();
        e1.EmployeeId = 1;
        EmployeeEntity e2 = new EmployeeEntity();
        e2.EmployeeId = 2;
        List<EmployeeEntity> listado = Arrays.asList(e1, e2);

        when(employeeRepository.listAll()).thenReturn(listado);

        List<EmployeeEntity> result = employeeBusiness.getAllEmpleados();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(1, result.get(0).EmployeeId);
    }

    @Test
    public void testGetEmpleadoById() {
        Long id = 100L;
        EmployeeEntity e = new EmployeeEntity();
        e.EmployeeId = 100;

        @SuppressWarnings("unchecked")
        PanacheQuery<EmployeeEntity> mockQuery = Mockito.mock(PanacheQuery.class);
        when(employeeRepository.find("EmployeeId", id)).thenReturn(mockQuery);
        when(mockQuery.firstResult()).thenReturn(e);

        EmployeeEntity result = employeeBusiness.getEmpleadoById(id);

        assertNotNull(result);
        assertEquals(100, result.EmployeeId);
    }

    @Test
    public void testCreateEmpleado() {
        EmployeeEntity e = new EmployeeEntity();
        e.EmployeeId = 5;
        doNothing().when(employeeRepository).persist(e);

        EmployeeEntity created = employeeBusiness.createEmpleado(e);

        assertSame(e, created);
        verify(employeeRepository, times(1)).persist(e);
    }

    @Test
    public void testActualizarEmpleado_existing() {
        Long id = 10L;
        EmployeeEntity existing = new EmployeeEntity();
        existing.EmployeeId = 10;
        existing.Name = "Juan";

        EmployeeEntity updated = new EmployeeEntity();
        updated.EmployeeId = 10;
        updated.Name = "Carlos";

        when(employeeRepository.findById(id)).thenReturn(existing);

        EmployeeEntity result = employeeBusiness.actualizarEmpleado(id, updated);

        assertNotNull(result);
        assertEquals("Carlos", result.Name);
    }

    @Test
    public void testActualizarEmpleado_notFound() {
        Long id = 99L;
        EmployeeEntity updated = new EmployeeEntity();
        when(employeeRepository.findById(id)).thenReturn(null);

        EmployeeEntity result = employeeBusiness.actualizarEmpleado(id, updated);

        assertNull(result);
    }

    @Test
    public void testDeleteEmpleado() {
        Long id = 7L;
        EmployeeEntity existing = new EmployeeEntity();
        existing.EmployeeId = 7;

        when(employeeRepository.findById(id)).thenReturn(existing);
        doNothing().when(employeeRepository).delete(existing);

        employeeBusiness.deleteEmpleado(id);

        verify(employeeRepository, times(1)).delete(existing);
    }
}
