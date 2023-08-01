package entity;

import java.time.LocalDate;

public record Addr(int id, String name, LocalDate startDate, LocalDate endDate) {
}
