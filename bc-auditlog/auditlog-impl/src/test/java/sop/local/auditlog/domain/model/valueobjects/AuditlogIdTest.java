package sop.local.auditlog.domain.model.valueobjects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;

import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import sop.local.valueobjects.utils.UUIDUtil;

public class AuditlogIdTest {

    /******************* Happypath tests *********************************/

    /* Whitebox test */
    
    @Test
    public void happyPath_ctor_Mocked_shouldReturnNewObject() {
        UUID testValue = UUID.randomUUID();
        try(MockedStatic<UUIDUtil> mocked = Mockito.mockStatic(UUIDUtil.class)) {
            mocked.when(() -> UUIDUtil.require(Mockito.any(UUID.class))).thenReturn(testValue);
            AuditlogId id = new AuditlogId(testValue);
            assertNotNull(id);
            assertEquals(testValue, id.value());
            mocked.verify(() -> new AuditlogId(testValue));
            mocked.verify(() -> UUIDUtil.require(testValue));
            mocked.verifyNoMoreInteractions();
        }
    }

    
    @Test
    public void happyPath_newId_Mocked_ShouldReturnNewObject() {
        UUID testValue = UUID.randomUUID();
        try(MockedStatic<UUIDUtil> mocked = Mockito.mockStatic(UUIDUtil.class)) {
            mocked.when(UUIDUtil::newUuid).thenReturn(testValue);
            mocked.when(() -> UUIDUtil.require(Mockito.any(UUID.class))).thenAnswer(inv -> inv.getArgument(0));
            AuditlogId id = AuditlogId.newId();
            assertNotNull(id);
            assertNotNull(id.value());
            assertEquals(testValue, id.value());
            mocked.verify(() -> UUIDUtil.require(testValue), Mockito.times(1));
            mocked.verify(() -> UUIDUtil.newUuid(), Mockito.times(1));
            mocked.verifyNoMoreInteractions();
        }
    }

    @Test
    public void happyPath_of_Mocked_ShouldReturnNewObject() {
        UUID testValue = UUID.randomUUID();
        String testValueStr = testValue.toString();
        try(MockedStatic<UUIDUtil> mocked = Mockito.mockStatic(UUIDUtil.class)) {
            mocked.when(() -> UUIDUtil.parseRequired(Mockito.anyString())).thenReturn(testValue);
            mocked.when(() -> UUIDUtil.require(Mockito.any(UUID.class))).thenAnswer(inv -> inv.getArgument(0));
            AuditlogId id = AuditlogId.of(testValueStr);
            assertNotNull(id);
            assertNotNull(id.value());
            assertEquals(testValueStr, id.value().toString());
            mocked.verify(() -> UUIDUtil.require(testValue), Mockito.times(1));
            mocked.verify(() -> UUIDUtil.parseRequired(testValueStr), Mockito.times(1));
            mocked.verifyNoMoreInteractions();
        }
    }
        

    /* Blackbox test */

    @Test
    public void happypath_ctor_noMock_ShoulddReturnNewObject() {
        AuditlogId id = new AuditlogId(UUID.randomUUID());
        assertNotNull(id);
        assertNotNull(id.value());
    }

    @Test
    public void happyPath_newId_NoMock_ShoulddReturnNewObject() {
        AuditlogId id = AuditlogId.newId();
        assertNotNull(id);
        assertNotNull(id.value());
    }

    @Test
    public void happyPath_of_noMock_ShoulddReturnNewObject() {
        String uuidStr = "123e4567-e89b-12d3-a456-426614174000";
        AuditlogId id = AuditlogId.of(uuidStr);
        assertNotNull(id);
        assertNotNull(id.value());
        assertEquals(uuidStr, id.value().toString());

    }

    @Test
    public void happyPath_fromString_noMock_ShoulddReturnNewObject() {
        String uuidStr = "123e4567-e89b-12d3-a456-426614174000";
        AuditlogId id = AuditlogId.fromString(uuidStr);
        assertNotNull(id);
        assertNotNull(id.value());
        assertEquals(uuidStr, id.value().toString());

    }

    /* ************* unhappy paths ***************** */

    /* Whitebox */

    @Test
    public void unhappypath_ctor_mocked_null_shouldThrowException() {
        try(MockedStatic<UUIDUtil> mocked = Mockito.mockStatic(UUIDUtil.class)) {
            mocked.when(() -> UUIDUtil.require(Mockito.any())).thenThrow(new IllegalArgumentException("UUID cannot be null"));
            var ex = assertThrows(IllegalArgumentException.class, () -> new AuditlogId(null));
            assertEquals("UUID cannot be null", ex.getMessage());
        }
    }

    @Test
    public void unhappypath_of_mocked_null_shouldThrowException() {
        try(MockedStatic<UUIDUtil> mocked = Mockito.mockStatic(UUIDUtil.class)) {
            mocked.when(() -> UUIDUtil.parseRequired(any())).thenThrow(new IllegalArgumentException("UUID string cannot be null or blank"));
             var ex1 = assertThrows(IllegalArgumentException.class, () -> AuditlogId.of(null));
             var ex2 = assertThrows(IllegalArgumentException.class, () -> AuditlogId.of("        "));
             assertEquals("UUID string cannot be null or blank", ex1.getMessage());
             assertEquals("UUID string cannot be null or blank", ex2.getMessage());

        }
    }
    

    /* black box */

    @Test
    public void unhappypath_ctor_noMock_null_shouldThrowException() {
        var ex = assertThrows(IllegalArgumentException.class, () -> new AuditlogId(null));
        assertEquals("UUID cannot be null", ex.getMessage());
    }

    @Test
    public void unhappypath_of_noMock_null_shouldThrowException() {
        var ex = assertThrows(IllegalArgumentException.class, () -> AuditlogId.of(null));
        assertEquals("UUID string cannot be null or blank", ex.getMessage());
    }


    @Test
    public void unhappypath_of_noMock_blank_shouldThrowException() {
        var ex = assertThrows(IllegalArgumentException.class, () -> AuditlogId.of("     "));
        assertEquals("UUID string cannot be null or blank", ex.getMessage());
    }



}
