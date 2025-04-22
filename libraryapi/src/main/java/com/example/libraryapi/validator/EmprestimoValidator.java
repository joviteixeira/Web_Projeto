// Arquivo: EmprestimoValidator.java
package com.example.libraryapi.validator;

import com.example.libraryapi.execptions.CampoInvalidoExpection;
import com.example.libraryapi.model.Emprestimo;
import org.springframework.stereotype.Component;

@Component
public class EmprestimoValidator {

    public void validar(Emprestimo emprestimo) {
        if (emprestimo.getDataDevolucaoPrevista().isBefore(emprestimo.getDataEmprestimo())) {
            throw new CampoInvalidoExpection("dataDevolucaoPrevista", "Data de devolução prevista inválida");
        }
    }

    public void validarDataDevolucaoReal(Emprestimo emprestimo) {
        if (emprestimo.getDataDevolucaoReal() != null &&
                emprestimo.getDataDevolucaoReal().isBefore(emprestimo.getDataEmprestimo())) {
            throw new CampoInvalidoExpection("dataDevolucaoReal", "Data de devolução não pode ser anterior ao empréstimo");
        }
    }
}