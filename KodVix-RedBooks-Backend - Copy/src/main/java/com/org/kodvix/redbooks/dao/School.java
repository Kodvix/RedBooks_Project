package com.org.kodvix.redbooks.dao;

import jakarta.persistence.*;
import lombok.*;

@Entity
@DiscriminatorValue("SCHOOL")
@Getter
@Setter
@NoArgsConstructor

public class School extends User{

}
