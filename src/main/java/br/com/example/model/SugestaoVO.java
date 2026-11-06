package br.com.example.model;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SugestaoVO implements Serializable {
    private String codigo;
    private String nome;
    private String sigla;
}
