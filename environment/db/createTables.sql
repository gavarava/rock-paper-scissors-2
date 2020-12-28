CREATE TABLE public.players
(
    id           int4         NOT NULL GENERATED ALWAYS AS IDENTITY,
    creationDate TIMESTAMPTZ DEFAULT now() NOT NULL,
    name         varchar(200) NOT NULL,
    CONSTRAINT player_pkey PRIMARY KEY (id)
);