select l1_0.id,a1_0.id,a1_0.data_nascimento,a1_0.nacionalidade,a1_0.name,l1_0.data_publicacao,l1_0.genero,l1_0.isbn,l1_0.preco,l1_0.titulo
from livro l1_0
left join public.autor a1_0 on a1_0.id=l1_0.id_autor
where l1_0.id=?