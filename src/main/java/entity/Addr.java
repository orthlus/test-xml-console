package entity;

import java.time.LocalDate;

public record Addr(int id, String name, String typeName, LocalDate startDate, LocalDate endDate, boolean isActual, boolean isActive) {
}
