package com.jordan.minhasfinancas.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jordan.minhasfinancas.model.entity.Lancamento;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long>{

}
