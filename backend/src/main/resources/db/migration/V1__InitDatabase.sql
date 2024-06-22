CREATE TABLE IF NOT EXISTS public.user
(
    id integer NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 0 MINVALUE 0 MAXVALUE 2147483647 CACHE 1 ),
    email character varying(100) COLLATE pg_catalog."default" NOT NULL,
    username character varying(100) COLLATE pg_catalog."default" NOT NULL,
    password character varying COLLATE pg_catalog."default" NOT NULL,
    role character varying COLLATE pg_catalog."default" NOT NULL,
    is_email_verified BOOLEAN DEFAULT false,
    CONSTRAINT user_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS public.category
(
    id integer NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 0 MINVALUE 0 MAXVALUE 2147483647 CACHE 1 ),
    title character varying(100) COLLATE pg_catalog."default" NOT NULL,
    description text COLLATE pg_catalog."default" NOT NULL,
    user_id integer,
    CONSTRAINT category_pkey PRIMARY KEY (id),
    CONSTRAINT fk_user_fk
        FOREIGN KEY (user_id)
            REFERENCES public.user (id)
            ON UPDATE NO ACTION
            ON DELETE NO ACTION
);

CREATE TABLE IF NOT EXISTS public.transaction
(
    id integer NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 0 MINVALUE 0 MAXVALUE 2147483647 CACHE 1 ),
    description text COLLATE pg_catalog."default" NOT NULL,
    type character varying COLLATE pg_catalog."default" NOT NULL,
    amount double precision NOT NULL,
    currency character varying COLLATE pg_catalog."default" NOT NULL,
    date date NOT NULL,
    user_id integer NOT NULL,
    category_id integer NOT NULL,
    CONSTRAINT transaction_pkey PRIMARY KEY (id),
    CONSTRAINT fk_user_fk
        FOREIGN KEY (user_id)
            REFERENCES public.user (id)
            ON UPDATE NO ACTION
            ON DELETE NO ACTION,
    CONSTRAINT fk_category_fk
        FOREIGN KEY (category_id)
            REFERENCES public.category (id)
            ON UPDATE NO ACTION
            ON DELETE NO ACTION
);
