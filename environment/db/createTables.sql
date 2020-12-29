CREATE TABLE public.players
(
    id           int4         NOT NULL GENERATED ALWAYS AS IDENTITY,
    creation_date TIMESTAMPTZ DEFAULT now() NOT NULL,
    name         varchar(200) NOT NULL,
    games_played int DEFAULT 0,
    CONSTRAINT player_pkey PRIMARY KEY (id)
);