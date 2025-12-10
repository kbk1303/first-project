package sop.local.valueobjects.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mockStatic;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

public class UUIDUtilTest {

    @Test
    void happypath_parseRequired_validUUID() {

        UUID expected = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");

        try(MockedStatic<UUID> mocked = mockStatic(UUID.class)) {
            mocked.when( () -> UUID.fromString(anyString()))
                .thenReturn(expected);
            UUID actual = UUIDUtil.parseRequired("123e4567-e89b-12d3-a456-426614174000");
            assertEquals(actual, expected);
        }
    }

    @Test
    void happypath_require_valid_UUID_shouldReturnUUID() {
        UUID testUuid = UUID.randomUUID();
        UUID result = UUIDUtil.require(testUuid);
        assertEquals(testUuid, result);
    }

    @Test
    void unhappyPath_require_null_shouldThrowException() {
        var ex = assertThrows(IllegalArgumentException.class, () -> UUIDUtil.require(null));
        assertEquals(ex.getMessage(), "UUID cannot be null");
    }

    @Test
    void unhappyPath_parseRequired_invalidUUID() {
        
        try (MockedStatic<UUID> mocked = mockStatic(UUID.class)) {
            mocked.when(() -> UUID.fromString(anyString()))
                    .thenAnswer(inv -> {
                    String s = inv.getArgument(0, String.class);
                    throw new IllegalArgumentException("Invalid UUID format: " + s);
                    });

            var ex = assertThrows(IllegalArgumentException.class,
                                    () -> UUIDUtil.parseRequired("invalid-uuid-string"));

            assertEquals(ex.getMessage(), "Invalid UUID format: invalid-uuid-string");

            // valgfrit: bekræft at stubben blev ramt med det rigtige argument
            mocked.verify(() -> UUID.fromString("invalid-uuid-string"));
        }
  
        
    }

    @Test
    void unhappypath_parseRequired_null_shouldThrowException() {
        
        var ex = assertThrows(IllegalArgumentException.class, () -> UUIDUtil.parseRequired(null)) ;   
        assertEquals(ex.getMessage(), "UUID string cannot be null or blank");
      
    }

     @Test
    void unhappypath_parseRequired_blank_shouldThrowException() {
        
        var ex = assertThrows(IllegalArgumentException.class, () -> UUIDUtil.parseRequired("   ")) ;   
        assertEquals(ex.getMessage(), "UUID string cannot be null or blank");
      
    }

    @Test
    void happypath_newUUID_generatesUUID() {
        
        try (MockedStatic<UUID> mocked = mockStatic(UUID.class)) {
            UUID expected = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");
            mocked.when(UUID::randomUUID).thenReturn(expected);

            UUID actual = UUIDUtil.newUuid();
            assertEquals(actual, expected);
        }

    }

    @Test
    void happypath_contructor_is_private() throws Exception {
        var constructor = UUIDUtil.class.getDeclaredConstructor();
        assertFalse(constructor.canAccess(null));   
    }

    


}
