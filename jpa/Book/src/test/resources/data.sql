--call next value for hibernate_sequence;
insert into user (`id`, `name`, `email`, `created_at`, `updated_at`) values (1, 'martin', 'martin@fastcampus.com', now(), now());

--call next value for hibernate_sequence;
insert into user (`id`, `name`, `email`, `created_at`, `updated_at`) values (2, 'dennis', 'dennis@fastcampus.com', now(), now());

--call next value for hibernate_sequence;
insert into user (`id`, `name`, `email`, `created_at`, `updated_at`) values (3, 'sophia', 'sophia@slowcampus.com', now(), now());

--call next value for hibernate_sequence;
insert into user (`id`, `name`, `email`, `created_at`, `updated_at`) values (4, 'james', 'james@slowcampus.com', now(), now());

--call next value for hibernate_sequence;
insert into user (`id`, `name`, `email`, `created_at`, `updated_at`) values (5, 'martin', 'martin@another.com', now(), now());

insert into publisher(`id`, `name`) values (1,'패스트캠퍼스');
insert into book(`id`, `name`, `publisher_id`,`deleted`,`status`) values (1,'JPA1',1,false,100);
insert into book(`id`, `name`, `publisher_id`,`deleted`,`status`) values (2,'JPA2',1,false,200);
insert into book(`id`, `name`, `publisher_id`,`deleted`,`status`) values (3,'JPA3',1,true,100);

insert into review(`id`, `title`, `content`,`score`,`user_id`,`book_id`) values (1,'good book','very good',5.0,1,1);
insert into review(`id`, `title`, `content`,`score`,`user_id`,`book_id`) values (2,'fast education','not bad',3.0,2,2);

insert into comment(`id`, `comment`, `review_id`) values (1,'me to good',1);
insert into comment(`id`, `comment`, `review_id`) values (2,'so so',1);

insert into comment(`id`, `comment`, `review_id`) values (3,'me to good',2);
insert into comment(`id`, `comment`, `review_id`) values (4,'so so',2);