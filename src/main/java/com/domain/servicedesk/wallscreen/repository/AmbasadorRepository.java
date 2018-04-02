package com.domain.servicedesk.wallscreen.repository;

import java.util.List;

import com.domain.servicedesk.wallscreen.jpa.Ambasador;

public interface AmbasadorRepository {

  List<Ambasador> getAllAmbasadors();
  
  List<Ambasador> getAmbasadorsByScreenId(Long id);
  
  List<Ambasador> getAmbasadorsByScreenTitle(String title);
  
  Ambasador getAmbasadorById(Long id);

  Ambasador createAmbasador(Ambasador jpa);
  
  Ambasador updateAmbasador(Ambasador jpa);
  
  void deleteAmbasadorById(Long id);

}
