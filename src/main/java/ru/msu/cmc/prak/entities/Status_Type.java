package ru.msu.cmc.prak.entities;


import org.hibernate.annotations.Type;
import org.hibernate.type.SqlTypes;

import java.io.Serializable;


public enum Status_Type implements Serializable {
    issued,
    in_progress,
    done,
    canceled
}