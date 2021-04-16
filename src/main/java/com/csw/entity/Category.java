package com.csw.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.stereotype.Component;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Component
public class Category {
    private String id;
    private String name;
}
