package com.example.DesafioTecnico.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Logger para registrar exceções inesperadas
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // Trata exceção lançada quando um cliente não é encontrado
    @ExceptionHandler(ClienteNotFoundException.class)
    public ResponseEntity<StandardError> handleClienteNotFound(ClienteNotFoundException ex,HttpServletRequest request) {
        StandardError erroPadrao = montarErroPadrao(
                HttpStatus.NOT_FOUND,
                "Cliente não encontrado",
                ex.getMessage(),
                request.getRequestURI());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erroPadrao);
    }

    // Trata exceção lançada quando uma conta não é encontrada
    @ExceptionHandler(ContaNotFoundException.class)
    public ResponseEntity<StandardError> handleContaNotFound(ContaNotFoundException ex,HttpServletRequest request) {
        StandardError erroPadrao = montarErroPadrao(
                HttpStatus.NOT_FOUND,
                "Conta não encontrada",
                ex.getMessage(),
                request.getRequestURI());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erroPadrao);
    }

    // Trata violação de regra de negócio
    @ExceptionHandler(RegraNegocioException.class)
    public ResponseEntity<StandardError> handleRegraNegocio(RegraNegocioException ex,HttpServletRequest request) {
        StandardError erroPadrao = montarErroPadrao(
                HttpStatus.BAD_REQUEST,
                "Violação de regra de negócio",
                ex.getMessage(),
                request.getRequestURI());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erroPadrao);
    }

    // Trata erros de validação de campos (Bean Validation)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationError> handleValidation(MethodArgumentNotValidException ex, HttpServletRequest request) {
        ValidationError erroValidacao = new ValidationError();
        erroValidacao.setInstante(Instant.now());
        erroValidacao.setStatus(HttpStatus.BAD_REQUEST.value());
        erroValidacao.setErro("Erro de validação");
        erroValidacao.setMensagem("Dados inválidos");
        erroValidacao.setCaminho(request.getRequestURI());

        // Adiciona cada erro de campo ao objeto de resposta
        ex.getBindingResult().getFieldErrors().forEach(e -> erroValidacao.adicionarErroCampo(e.getField(), e.getDefaultMessage()));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erroValidacao);
    }

    // Trata parâmetros de rota com tipo incompatível (ex.: id não numérico)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<StandardError> handlePathParamError(MethodArgumentTypeMismatchException ex, HttpServletRequest request) {
        String mensagem = "Parâmetro inválido: " + ex.getName();
        StandardError erroPadrao = montarErroPadrao(
                HttpStatus.BAD_REQUEST,
                "Parâmetro inválido",
                mensagem,
                request.getRequestURI());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erroPadrao);
    }

    // Captura exceções genéricas não tratadas explicitamente
    @ExceptionHandler(Exception.class)
    public ResponseEntity<StandardError> handleException(Exception ex,HttpServletRequest request) {log.error("Erro inesperado", ex); // Registra o stack trace para diagnóstico

        StandardError erroPadrao = montarErroPadrao(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Erro inesperado",
                "Ocorreu um erro interno no servidor",
                request.getRequestURI());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(erroPadrao);
    }

    // Método utilitário para criar objeto de erro padronizado
    private StandardError montarErroPadrao(HttpStatus status,String erro,String mensagem,String caminho) {
        StandardError erroPadrao = new StandardError();
        erroPadrao.setInstante(Instant.now());
        erroPadrao.setStatus(status.value());
        erroPadrao.setErro(erro);
        erroPadrao.setMensagem(mensagem);
        erroPadrao.setCaminho(caminho);
        return erroPadrao;
    }
}
