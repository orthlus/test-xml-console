package entity;

import java.time.LocalDate;

public record HierarchyItem(int id, int parentId, LocalDate startDate, LocalDate endDate, boolean isActive) {
}
