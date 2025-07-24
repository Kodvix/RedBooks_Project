package com.org.kodvix.redbooks.dao;
import jakarta.persistence.*;
import lombok.*;
@Entity
@DiscriminatorValue("PUBLISHER")
@Getter
@Setter
@NoArgsConstructor

public class Publisher extends User{
}
