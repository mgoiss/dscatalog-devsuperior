package com.vobidu.dscatalog.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vobidu.dscatalog.dto.CategoryDTO;
import com.vobidu.dscatalog.entities.Category;
import com.vobidu.dscatalog.repositories.CategoryRepository;
import com.vobidu.dscatalog.services.exceptions.EntityNotFoundException;

@Service
public class CategoryService {
	
	@Autowired
	private CategoryRepository repository;
	
	@Transactional(readOnly = true)
	public List<CategoryDTO> findAll() {
		List<Category> list = repository.findAll();
		
		List<CategoryDTO> listDto = list.stream().map(x -> new CategoryDTO(x)).collect(Collectors.toList());
		
		return listDto;
	}

	@Transactional(readOnly = true)
	public CategoryDTO findById(Long id) {
		Optional<Category> obj = repository.findById(id);
		//Pegando o objeto dentro do Optional, no caso a Categoria
		Category entity = obj.orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada"));
		return new CategoryDTO(entity);
	}
	
	@Transactional(readOnly = true)
	public CategoryDTO insert(CategoryDTO dto) {
		Category entity = new Category();
		entity.setName(dto.getName());
		entity = repository.save(entity);
		return new CategoryDTO(entity);
	}
}
