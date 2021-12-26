package com.example.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author yyt
 * @date 2021年12月21日 19:19
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MztImage implements Serializable {

    private static final long serialVersionUID = 1221L;

    private Integer id;

    private String name;

    private String avatar;

}
