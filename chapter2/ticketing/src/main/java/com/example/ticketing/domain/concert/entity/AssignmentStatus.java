package com.example.ticketing.domain.concert.entity;

/**
 * 굳이 AssignmentStatus가 필요한지 고민해볼 필요가 있음.
 */
public enum AssignmentStatus {
    NOT_BE_ASSIGNED("임시 배정이 안된 좌석"),
    ASSIGNED("임시 배정된 좌석"),
    NOT_ASSIGNABLE("임시 배정할 수 없는 좌석");

    String desc;

    AssignmentStatus(String desc) {
        this.desc = desc;
    }
}
