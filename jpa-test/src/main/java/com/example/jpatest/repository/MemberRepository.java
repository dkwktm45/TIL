package com.example.jpatest.repository;

import com.example.jpatest.entity.Member;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MemberRepository extends JpaRepository<Member,Long> {

	@EntityGraph(attributePaths = {"boards"})
	Member findByMemberId(Long id);
	@Query(
			"select m from Member m left join fetch m.boards"
	)
	Member findByCustomId(Long id);
}
