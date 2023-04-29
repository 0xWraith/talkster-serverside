--
-- PostgreSQL database dump
--

-- Dumped from database version 15.2
-- Dumped by pg_dump version 15.1

-- Started on 2023-04-29 23:45:45

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 214 (class 1259 OID 49552)
-- Name: accounts; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.accounts (
    id integer NOT NULL,
    firstname character varying(16) NOT NULL,
    lastname character varying(16) DEFAULT NULL::character varying,
    mail character varying(64) NOT NULL,
    username character varying(32) DEFAULT NULL::character varying,
    image_id bigint,
    biography character varying(71)
);


ALTER TABLE public.accounts OWNER TO postgres;

--
-- TOC entry 3449 (class 0 OID 0)
-- Dependencies: 214
-- Name: TABLE accounts; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON TABLE public.accounts IS 'User accounts';


--
-- TOC entry 215 (class 1259 OID 49557)
-- Name: accounts_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.accounts_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.accounts_id_seq OWNER TO postgres;

--
-- TOC entry 3450 (class 0 OID 0)
-- Dependencies: 215
-- Name: accounts_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.accounts_id_seq OWNED BY public.accounts.id;


--
-- TOC entry 216 (class 1259 OID 49558)
-- Name: auth_keys; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.auth_keys (
    id integer NOT NULL,
    ownerid bigint NOT NULL,
    mail character varying(64) NOT NULL,
    code character varying(6) NOT NULL
);


ALTER TABLE public.auth_keys OWNER TO postgres;

--
-- TOC entry 217 (class 1259 OID 49561)
-- Name: auth_keys_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.auth_keys_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.auth_keys_id_seq OWNER TO postgres;

--
-- TOC entry 3451 (class 0 OID 0)
-- Dependencies: 217
-- Name: auth_keys_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.auth_keys_id_seq OWNED BY public.auth_keys.id;


--
-- TOC entry 218 (class 1259 OID 49562)
-- Name: chats; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.chats (
    id bigint NOT NULL,
    owner_id bigint NOT NULL,
    receiver_id bigint NOT NULL,
    updated_at character varying(40),
    mute_time integer DEFAULT 0,
    chat_type integer NOT NULL
);


ALTER TABLE public.chats OWNER TO postgres;

--
-- TOC entry 3452 (class 0 OID 0)
-- Dependencies: 218
-- Name: TABLE chats; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON TABLE public.chats IS 'Table of chats';


--
-- TOC entry 219 (class 1259 OID 49565)
-- Name: chats_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.chats_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.chats_id_seq OWNER TO postgres;

--
-- TOC entry 3453 (class 0 OID 0)
-- Dependencies: 219
-- Name: chats_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.chats_id_seq OWNED BY public.chats.id;


--
-- TOC entry 220 (class 1259 OID 49566)
-- Name: files; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.files (
    id integer NOT NULL,
    name character varying(64) NOT NULL,
    type character varying(96) NOT NULL
);


ALTER TABLE public.files OWNER TO postgres;

--
-- TOC entry 221 (class 1259 OID 49569)
-- Name: files_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.files_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.files_id_seq OWNER TO postgres;

--
-- TOC entry 3454 (class 0 OID 0)
-- Dependencies: 221
-- Name: files_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.files_id_seq OWNED BY public.files.id;


--
-- TOC entry 229 (class 1259 OID 57861)
-- Name: group_chats; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.group_chats (
    id bigint NOT NULL,
    name character varying NOT NULL,
    updated_at timestamp without time zone DEFAULT now() NOT NULL
);


ALTER TABLE public.group_chats OWNER TO postgres;

--
-- TOC entry 228 (class 1259 OID 57860)
-- Name: group_chats_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.group_chats_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.group_chats_id_seq OWNER TO postgres;

--
-- TOC entry 3455 (class 0 OID 0)
-- Dependencies: 228
-- Name: group_chats_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.group_chats_id_seq OWNED BY public.group_chats.id;


--
-- TOC entry 233 (class 1259 OID 57908)
-- Name: group_messages; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.group_messages (
    id bigint NOT NULL,
    group_id bigint NOT NULL,
    sender_id bigint NOT NULL,
    message_type integer NOT NULL,
    message_content character varying(4097) NOT NULL,
    created_at character varying DEFAULT now() NOT NULL
);


ALTER TABLE public.group_messages OWNER TO postgres;

--
-- TOC entry 232 (class 1259 OID 57907)
-- Name: group_messages_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.group_messages_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.group_messages_id_seq OWNER TO postgres;

--
-- TOC entry 3456 (class 0 OID 0)
-- Dependencies: 232
-- Name: group_messages_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.group_messages_id_seq OWNED BY public.group_messages.id;


--
-- TOC entry 231 (class 1259 OID 57890)
-- Name: groups; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.groups (
    id bigint NOT NULL,
    user_id bigint NOT NULL,
    group_id bigint NOT NULL
);


ALTER TABLE public.groups OWNER TO postgres;

--
-- TOC entry 230 (class 1259 OID 57889)
-- Name: groups_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.groups_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.groups_id_seq OWNER TO postgres;

--
-- TOC entry 3457 (class 0 OID 0)
-- Dependencies: 230
-- Name: groups_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.groups_id_seq OWNED BY public.groups.id;


--
-- TOC entry 222 (class 1259 OID 49570)
-- Name: messages; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.messages (
    id bigint NOT NULL,
    chat_id bigint NOT NULL,
    sender_id bigint NOT NULL,
    receiver_id bigint NOT NULL,
    message_type integer NOT NULL,
    message_content character varying(4097) NOT NULL,
    created_at character varying(50) NOT NULL
);


ALTER TABLE public.messages OWNER TO postgres;

--
-- TOC entry 223 (class 1259 OID 49575)
-- Name: messages_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.messages_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.messages_id_seq OWNER TO postgres;

--
-- TOC entry 3458 (class 0 OID 0)
-- Dependencies: 223
-- Name: messages_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.messages_id_seq OWNED BY public.messages.id;


--
-- TOC entry 224 (class 1259 OID 49576)
-- Name: tokens; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.tokens (
    id integer NOT NULL,
    owner_id bigint NOT NULL,
    token character varying(255) NOT NULL,
    updated_at timestamp without time zone NOT NULL
);


ALTER TABLE public.tokens OWNER TO postgres;

--
-- TOC entry 225 (class 1259 OID 49579)
-- Name: tokens_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.tokens_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.tokens_id_seq OWNER TO postgres;

--
-- TOC entry 3459 (class 0 OID 0)
-- Dependencies: 225
-- Name: tokens_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.tokens_id_seq OWNED BY public.tokens.id;


--
-- TOC entry 227 (class 1259 OID 57843)
-- Name: user_contacts; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.user_contacts (
    id bigint NOT NULL,
    user_id bigint NOT NULL,
    contact_id bigint NOT NULL
);


ALTER TABLE public.user_contacts OWNER TO postgres;

--
-- TOC entry 226 (class 1259 OID 57842)
-- Name: user_contacts_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.user_contacts_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.user_contacts_id_seq OWNER TO postgres;

--
-- TOC entry 3460 (class 0 OID 0)
-- Dependencies: 226
-- Name: user_contacts_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.user_contacts_id_seq OWNED BY public.user_contacts.id;


--
-- TOC entry 3218 (class 2604 OID 49580)
-- Name: accounts id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.accounts ALTER COLUMN id SET DEFAULT nextval('public.accounts_id_seq'::regclass);


--
-- TOC entry 3221 (class 2604 OID 49581)
-- Name: auth_keys id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.auth_keys ALTER COLUMN id SET DEFAULT nextval('public.auth_keys_id_seq'::regclass);


--
-- TOC entry 3222 (class 2604 OID 49582)
-- Name: chats id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.chats ALTER COLUMN id SET DEFAULT nextval('public.chats_id_seq'::regclass);


--
-- TOC entry 3224 (class 2604 OID 49583)
-- Name: files id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.files ALTER COLUMN id SET DEFAULT nextval('public.files_id_seq'::regclass);


--
-- TOC entry 3228 (class 2604 OID 57864)
-- Name: group_chats id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.group_chats ALTER COLUMN id SET DEFAULT nextval('public.group_chats_id_seq'::regclass);


--
-- TOC entry 3231 (class 2604 OID 57911)
-- Name: group_messages id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.group_messages ALTER COLUMN id SET DEFAULT nextval('public.group_messages_id_seq'::regclass);


--
-- TOC entry 3230 (class 2604 OID 57893)
-- Name: groups id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.groups ALTER COLUMN id SET DEFAULT nextval('public.groups_id_seq'::regclass);


--
-- TOC entry 3225 (class 2604 OID 49584)
-- Name: messages id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.messages ALTER COLUMN id SET DEFAULT nextval('public.messages_id_seq'::regclass);


--
-- TOC entry 3226 (class 2604 OID 49585)
-- Name: tokens id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tokens ALTER COLUMN id SET DEFAULT nextval('public.tokens_id_seq'::regclass);


--
-- TOC entry 3227 (class 2604 OID 57846)
-- Name: user_contacts id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_contacts ALTER COLUMN id SET DEFAULT nextval('public.user_contacts_id_seq'::regclass);


--
-- TOC entry 3424 (class 0 OID 49552)
-- Dependencies: 214
-- Data for Name: accounts; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.accounts (id, firstname, lastname, mail, username, image_id, biography) VALUES (16, 'wraith', '', 'xdzhuha@gmail.com', NULL, NULL, NULL);
INSERT INTO public.accounts (id, firstname, lastname, mail, username, image_id, biography) VALUES (17, '123', '321', 'ilyayrch@gmail.com', NULL, NULL, NULL);
INSERT INTO public.accounts (id, firstname, lastname, mail, username, image_id, biography) VALUES (21, 'Kleeat', '', 'xszenasi@stuba.sk', NULL, NULL, NULL);
INSERT INTO public.accounts (id, firstname, lastname, mail, username, image_id, biography) VALUES (18, 'Levente', '', 'senasilevixd@gmail.com', NULL, 20, NULL);
INSERT INTO public.accounts (id, firstname, lastname, mail, username, image_id, biography) VALUES (22, 'test', 'bab', 'xdzhuha@stuba.sk', NULL, NULL, NULL);
INSERT INTO public.accounts (id, firstname, lastname, mail, username, image_id, biography) VALUES (20, 'Yaroslave', 'FEI', 'test@gmail.com', NULL, 27, NULL);
INSERT INTO public.accounts (id, firstname, lastname, mail, username, image_id, biography) VALUES (19, 'wraith', '', 'dimadjuga@gmail.com', 'mukiteam', 32, 'аллвлвоцрутвослащущущушоапошашв8у7угаоаггагпшпп');


--
-- TOC entry 3426 (class 0 OID 49558)
-- Dependencies: 216
-- Data for Name: auth_keys; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.auth_keys (id, ownerid, mail, code) VALUES (57, -1, 'xdzhuha@gmail.com', '39342');
INSERT INTO public.auth_keys (id, ownerid, mail, code) VALUES (58, -1, 'xdzhuha@gmail.com', '58338');
INSERT INTO public.auth_keys (id, ownerid, mail, code) VALUES (59, -1, 'test@gmail.com', '65522');
INSERT INTO public.auth_keys (id, ownerid, mail, code) VALUES (60, -1, 'xdzhuha@gmail.com', '30226');
INSERT INTO public.auth_keys (id, ownerid, mail, code) VALUES (61, -1, 'xdzhuha@gmail.com', '01259');
INSERT INTO public.auth_keys (id, ownerid, mail, code) VALUES (62, -1, 'xdzhuha@gmail.com', '45218');
INSERT INTO public.auth_keys (id, ownerid, mail, code) VALUES (63, -1, 'xdzhuha@gmail.com', '16560');
INSERT INTO public.auth_keys (id, ownerid, mail, code) VALUES (64, -1, 'xdzhuha@gmail.com', '09120');
INSERT INTO public.auth_keys (id, ownerid, mail, code) VALUES (65, 8, 'xdzhuha@gmail.com', '09840');
INSERT INTO public.auth_keys (id, ownerid, mail, code) VALUES (66, -1, 'xdzhuha@gmail.com', '96323');
INSERT INTO public.auth_keys (id, ownerid, mail, code) VALUES (67, 9, 'xdzhuha@gmail.com', '47887');
INSERT INTO public.auth_keys (id, ownerid, mail, code) VALUES (68, 9, 'xdzhuha@gmail.com', '81707');
INSERT INTO public.auth_keys (id, ownerid, mail, code) VALUES (69, -1, 'dimadjuga@gmail.com', '93304');
INSERT INTO public.auth_keys (id, ownerid, mail, code) VALUES (70, 10, 'dimadjuga@gmail.com', '17897');
INSERT INTO public.auth_keys (id, ownerid, mail, code) VALUES (71, -1, 'xshtepa@stuba.sk', '46851');
INSERT INTO public.auth_keys (id, ownerid, mail, code) VALUES (72, 11, 'xshtepa@stuba.sk', '67757');
INSERT INTO public.auth_keys (id, ownerid, mail, code) VALUES (73, -1, 'xdzhuha@gmail.com', '22336');
INSERT INTO public.auth_keys (id, ownerid, mail, code) VALUES (74, -1, 'xdzhuha@gmail.com', '05168');
INSERT INTO public.auth_keys (id, ownerid, mail, code) VALUES (75, 12, 'xdzhuha@gmail.com', '94625');
INSERT INTO public.auth_keys (id, ownerid, mail, code) VALUES (76, -1, 'xdzhuha@gmail.com', '82537');
INSERT INTO public.auth_keys (id, ownerid, mail, code) VALUES (77, -1, 'xdzhuha@gmail.com', '45042');
INSERT INTO public.auth_keys (id, ownerid, mail, code) VALUES (78, -1, 'xdzhuha@gmail.com', '43093');
INSERT INTO public.auth_keys (id, ownerid, mail, code) VALUES (79, -1, 'xdzhuha@gmail.com', '40273');
INSERT INTO public.auth_keys (id, ownerid, mail, code) VALUES (80, -1, 'ilyayrch@gmail.com', '57081');
INSERT INTO public.auth_keys (id, ownerid, mail, code) VALUES (81, -1, 'ilyayrch@gmail.com', '82738');
INSERT INTO public.auth_keys (id, ownerid, mail, code) VALUES (82, 16, 'xdzhuha@gmail.com', '59979');
INSERT INTO public.auth_keys (id, ownerid, mail, code) VALUES (83, -1, 'senasilevixd@gmail.com', '98527');
INSERT INTO public.auth_keys (id, ownerid, mail, code) VALUES (84, 16, 'xdzhuha@gmail.com', '51807');
INSERT INTO public.auth_keys (id, ownerid, mail, code) VALUES (85, -1, 'dimadjuga@gmail.com', '14135');
INSERT INTO public.auth_keys (id, ownerid, mail, code) VALUES (86, 17, 'ilyayrch@gmail.com', '32145');
INSERT INTO public.auth_keys (id, ownerid, mail, code) VALUES (87, -1, 'chirchenko333@gmail.com ', '95673');
INSERT INTO public.auth_keys (id, ownerid, mail, code) VALUES (88, 18, 'senasilevixd@gmail.com', '36221');
INSERT INTO public.auth_keys (id, ownerid, mail, code) VALUES (89, 18, 'senasilevixd@gmail.com', '09476');
INSERT INTO public.auth_keys (id, ownerid, mail, code) VALUES (90, 18, 'senasilevixd@gmail.com', '29907');
INSERT INTO public.auth_keys (id, ownerid, mail, code) VALUES (91, 18, 'senasilevixd@gmail.com', '03358');
INSERT INTO public.auth_keys (id, ownerid, mail, code) VALUES (92, 18, 'senasilevixd@gmail.com', '24123');
INSERT INTO public.auth_keys (id, ownerid, mail, code) VALUES (93, 18, 'senasilevixd@gmail.com', '47041');
INSERT INTO public.auth_keys (id, ownerid, mail, code) VALUES (94, 18, 'senasilevixd@gmail.com', '80903');
INSERT INTO public.auth_keys (id, ownerid, mail, code) VALUES (95, 18, 'senasilevixd@gmail.com', '90977');
INSERT INTO public.auth_keys (id, ownerid, mail, code) VALUES (96, 18, 'senasilevixd@gmail.com', '05856');
INSERT INTO public.auth_keys (id, ownerid, mail, code) VALUES (97, 18, 'senasilevixd@gmail.com', '43051');
INSERT INTO public.auth_keys (id, ownerid, mail, code) VALUES (98, 18, 'senasilevixd@gmail.com', '49569');
INSERT INTO public.auth_keys (id, ownerid, mail, code) VALUES (99, 18, 'senasilevixd@gmail.com', '23604');
INSERT INTO public.auth_keys (id, ownerid, mail, code) VALUES (100, -1, 'kleeat.yt@gmail.com', '62627');
INSERT INTO public.auth_keys (id, ownerid, mail, code) VALUES (101, -1, 'kleeat.yt@gmail.com', '16041');
INSERT INTO public.auth_keys (id, ownerid, mail, code) VALUES (102, -1, 'kleeat.yt@gmail.com', '60788');
INSERT INTO public.auth_keys (id, ownerid, mail, code) VALUES (103, -1, 'kleeat.yt@gmail.com', '21209');
INSERT INTO public.auth_keys (id, ownerid, mail, code) VALUES (104, 18, 'senasilevixd@gmail.com', '60635');
INSERT INTO public.auth_keys (id, ownerid, mail, code) VALUES (105, -1, 'kleeat.yt@gmail.com', '57062');
INSERT INTO public.auth_keys (id, ownerid, mail, code) VALUES (106, -1, 'kleeat.yt@gmail.com', '54330');
INSERT INTO public.auth_keys (id, ownerid, mail, code) VALUES (107, -1, 'xszenasi@stuba.sk', '68727');
INSERT INTO public.auth_keys (id, ownerid, mail, code) VALUES (108, -1, 'xszenasi@stuba.sk', '29513');
INSERT INTO public.auth_keys (id, ownerid, mail, code) VALUES (109, -1, 'xszenasi@stuba.sk', '29267');
INSERT INTO public.auth_keys (id, ownerid, mail, code) VALUES (110, -1, 'xszenasi@stuba.sk', '01463');
INSERT INTO public.auth_keys (id, ownerid, mail, code) VALUES (111, -1, 'xszenasi@stuba.sk', '71706');
INSERT INTO public.auth_keys (id, ownerid, mail, code) VALUES (112, 18, 'senasilevixd@gmail.com', '05684');
INSERT INTO public.auth_keys (id, ownerid, mail, code) VALUES (113, 19, 'dimadjuga@gmail.com', '43067');
INSERT INTO public.auth_keys (id, ownerid, mail, code) VALUES (114, 18, 'senasilevixd@gmail.com', '50874');
INSERT INTO public.auth_keys (id, ownerid, mail, code) VALUES (115, -1, 'xdzhuha@stuba.sk', '26595');
INSERT INTO public.auth_keys (id, ownerid, mail, code) VALUES (116, 22, 'xdzhuha@stuba.sk', '13493');


--
-- TOC entry 3428 (class 0 OID 49562)
-- Dependencies: 218
-- Data for Name: chats; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.chats (id, owner_id, receiver_id, updated_at, mute_time, chat_type) VALUES (100, 17, 20, '2023-04-29T20:51:50.831509200+02:00', 0, 0);
INSERT INTO public.chats (id, owner_id, receiver_id, updated_at, mute_time, chat_type) VALUES (101, 20, 17, '2023-04-29T20:51:50.838227300+02:00', 0, 0);
INSERT INTO public.chats (id, owner_id, receiver_id, updated_at, mute_time, chat_type) VALUES (102, 17, 19, '2023-04-29T20:52:11.077518400+02:00', 0, 0);
INSERT INTO public.chats (id, owner_id, receiver_id, updated_at, mute_time, chat_type) VALUES (103, 19, 17, '2023-04-29T20:52:11.079468+02:00', 0, 0);
INSERT INTO public.chats (id, owner_id, receiver_id, updated_at, mute_time, chat_type) VALUES (98, 19, 20, '2023-04-28T18:22:12.575747400+02:00', 0, 0);
INSERT INTO public.chats (id, owner_id, receiver_id, updated_at, mute_time, chat_type) VALUES (99, 20, 19, '2023-04-28T18:22:12.594239100+02:00', 0, 0);


--
-- TOC entry 3430 (class 0 OID 49566)
-- Dependencies: 220
-- Data for Name: files; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.files (id, name, type) VALUES (20, '0d0c8879-56e7-437a-97d4-8c3a0ade3d4e.jpg', 'image/jpeg');
INSERT INTO public.files (id, name, type) VALUES (27, '276a1605-f9a4-463b-aa47-3e79d5dd6ac1.jpg', 'image/jpeg');
INSERT INTO public.files (id, name, type) VALUES (32, 'abaf0275-ac22-4219-b682-2480bdf92a48.jpg', 'image/jpeg');


--
-- TOC entry 3439 (class 0 OID 57861)
-- Dependencies: 229
-- Data for Name: group_chats; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.group_chats (id, name, updated_at) VALUES (87, 'banditos', '2023-04-29 20:54:58.118094');
INSERT INTO public.group_chats (id, name, updated_at) VALUES (85, 'hello world', '2023-04-29 21:24:09.696783');
INSERT INTO public.group_chats (id, name, updated_at) VALUES (89, 'test', '2023-04-29 23:06:20.883446');
INSERT INTO public.group_chats (id, name, updated_at) VALUES (90, 'bab', '2023-04-29 23:16:53.285464');
INSERT INTO public.group_chats (id, name, updated_at) VALUES (88, 'banditos #2', '2023-04-29 23:17:04.660481');
INSERT INTO public.group_chats (id, name, updated_at) VALUES (91, 'privet', '2023-04-29 23:37:11.00991');
INSERT INTO public.group_chats (id, name, updated_at) VALUES (92, 'huliganos', '2023-04-29 23:40:51.006003');


--
-- TOC entry 3443 (class 0 OID 57908)
-- Dependencies: 233
-- Data for Name: group_messages; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.group_messages (id, group_id, sender_id, message_type, message_content, created_at) VALUES (1, 88, 19, 0, 'gggy', '2023-04-29T21:23:40.214+02:00');
INSERT INTO public.group_messages (id, group_id, sender_id, message_type, message_content, created_at) VALUES (2, 85, 19, 0, 'hehehe', '2023-04-29T21:24:09.984+02:00');
INSERT INTO public.group_messages (id, group_id, sender_id, message_type, message_content, created_at) VALUES (3, 88, 20, 0, 'brat', '2023-04-29T21:30:45.882+02:00');
INSERT INTO public.group_messages (id, group_id, sender_id, message_type, message_content, created_at) VALUES (4, 88, 19, 0, 'hello', '2023-04-29T22:54:52.941+02:00');
INSERT INTO public.group_messages (id, group_id, sender_id, message_type, message_content, created_at) VALUES (5, 88, 20, 0, 'heheha', '2023-04-29T22:55:56.214+02:00');
INSERT INTO public.group_messages (id, group_id, sender_id, message_type, message_content, created_at) VALUES (6, 89, 19, 0, 'hello', '2023-04-29T23:06:21.138+02:00');
INSERT INTO public.group_messages (id, group_id, sender_id, message_type, message_content, created_at) VALUES (7, 90, 19, 0, 'henlo', '2023-04-29T23:08:22.813+02:00');
INSERT INTO public.group_messages (id, group_id, sender_id, message_type, message_content, created_at) VALUES (8, 90, 19, 0, 'hej', '2023-04-29T23:08:41.208+02:00');
INSERT INTO public.group_messages (id, group_id, sender_id, message_type, message_content, created_at) VALUES (9, 90, 19, 0, 'hxhxhx', '2023-04-29T23:16:45.144+02:00');
INSERT INTO public.group_messages (id, group_id, sender_id, message_type, message_content, created_at) VALUES (10, 90, 19, 0, 'xhxyysysysysys', '2023-04-29T23:16:49.658+02:00');
INSERT INTO public.group_messages (id, group_id, sender_id, message_type, message_content, created_at) VALUES (11, 90, 19, 0, 'xhhxhdhshsj', '2023-04-29T23:16:53.443+02:00');
INSERT INTO public.group_messages (id, group_id, sender_id, message_type, message_content, created_at) VALUES (12, 88, 19, 0, 'xhxhhdudus', '2023-04-29T23:17+02:00');
INSERT INTO public.group_messages (id, group_id, sender_id, message_type, message_content, created_at) VALUES (13, 88, 19, 0, 'dhhxhdhdusus', '2023-04-29T23:17:04.795+02:00');
INSERT INTO public.group_messages (id, group_id, sender_id, message_type, message_content, created_at) VALUES (14, 91, 19, 0, 'hej', '2023-04-29T23:37:11.012+02:00');
INSERT INTO public.group_messages (id, group_id, sender_id, message_type, message_content, created_at) VALUES (15, 92, 19, 0, 'henlo', '2023-04-29T23:40:39.039+02:00');
INSERT INTO public.group_messages (id, group_id, sender_id, message_type, message_content, created_at) VALUES (16, 92, 20, 0, 'hej', '2023-04-29T23:40:36.349+02:00');


--
-- TOC entry 3441 (class 0 OID 57890)
-- Dependencies: 231
-- Data for Name: groups; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.groups (id, user_id, group_id) VALUES (280, 20, 85);
INSERT INTO public.groups (id, user_id, group_id) VALUES (281, 19, 85);
INSERT INTO public.groups (id, user_id, group_id) VALUES (284, 20, 88);
INSERT INTO public.groups (id, user_id, group_id) VALUES (285, 19, 88);
INSERT INTO public.groups (id, user_id, group_id) VALUES (286, 17, 88);
INSERT INTO public.groups (id, user_id, group_id) VALUES (287, 20, 89);
INSERT INTO public.groups (id, user_id, group_id) VALUES (288, 19, 89);
INSERT INTO public.groups (id, user_id, group_id) VALUES (289, 20, 90);
INSERT INTO public.groups (id, user_id, group_id) VALUES (290, 19, 90);
INSERT INTO public.groups (id, user_id, group_id) VALUES (291, 20, 91);
INSERT INTO public.groups (id, user_id, group_id) VALUES (292, 19, 91);
INSERT INTO public.groups (id, user_id, group_id) VALUES (293, 20, 92);
INSERT INTO public.groups (id, user_id, group_id) VALUES (294, 19, 92);


--
-- TOC entry 3432 (class 0 OID 49570)
-- Dependencies: 222
-- Data for Name: messages; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.messages (id, chat_id, sender_id, receiver_id, message_type, message_content, created_at) VALUES (1152, 98, 19, 20, 0, 'аосолаа', '2023-04-29T13:03:19.026+02:00');
INSERT INTO public.messages (id, chat_id, sender_id, receiver_id, message_type, message_content, created_at) VALUES (1153, 99, 19, 20, 0, 'аосолаа', '2023-04-29T13:03:19.026+02:00');
INSERT INTO public.messages (id, chat_id, sender_id, receiver_id, message_type, message_content, created_at) VALUES (1154, 98, 19, 20, 0, 'eu', '2023-04-29T14:14:24.220+02:00');
INSERT INTO public.messages (id, chat_id, sender_id, receiver_id, message_type, message_content, created_at) VALUES (1155, 99, 19, 20, 0, 'eu', '2023-04-29T14:14:24.220+02:00');
INSERT INTO public.messages (id, chat_id, sender_id, receiver_id, message_type, message_content, created_at) VALUES (1156, 98, 19, 20, 0, 'kekw', '2023-04-29T14:14:31.842+02:00');
INSERT INTO public.messages (id, chat_id, sender_id, receiver_id, message_type, message_content, created_at) VALUES (1157, 99, 19, 20, 0, 'kekw', '2023-04-29T14:14:31.842+02:00');
INSERT INTO public.messages (id, chat_id, sender_id, receiver_id, message_type, message_content, created_at) VALUES (1158, 98, 19, 20, 0, 'bab', '2023-04-29T14:14:35.667+02:00');
INSERT INTO public.messages (id, chat_id, sender_id, receiver_id, message_type, message_content, created_at) VALUES (1159, 99, 19, 20, 0, 'bab', '2023-04-29T14:14:35.667+02:00');
INSERT INTO public.messages (id, chat_id, sender_id, receiver_id, message_type, message_content, created_at) VALUES (1160, 98, 19, 20, 0, 'g6fyf', '2023-04-29T14:18:24.740+02:00');
INSERT INTO public.messages (id, chat_id, sender_id, receiver_id, message_type, message_content, created_at) VALUES (1161, 99, 19, 20, 0, 'g6fyf', '2023-04-29T14:18:24.740+02:00');
INSERT INTO public.messages (id, chat_id, sender_id, receiver_id, message_type, message_content, created_at) VALUES (1162, 98, 19, 20, 0, 'yyy', '2023-04-29T14:19:13.498+02:00');
INSERT INTO public.messages (id, chat_id, sender_id, receiver_id, message_type, message_content, created_at) VALUES (1163, 99, 19, 20, 0, 'yyy', '2023-04-29T14:19:13.498+02:00');
INSERT INTO public.messages (id, chat_id, sender_id, receiver_id, message_type, message_content, created_at) VALUES (1164, 98, 19, 20, 0, 'ллл', '2023-04-29T15:12:37.106+02:00');
INSERT INTO public.messages (id, chat_id, sender_id, receiver_id, message_type, message_content, created_at) VALUES (1165, 99, 19, 20, 0, 'ллл', '2023-04-29T15:12:37.106+02:00');
INSERT INTO public.messages (id, chat_id, sender_id, receiver_id, message_type, message_content, created_at) VALUES (1166, 98, 19, 20, 0, 'сшашша', '2023-04-29T15:46:29.393+02:00');
INSERT INTO public.messages (id, chat_id, sender_id, receiver_id, message_type, message_content, created_at) VALUES (1167, 99, 19, 20, 0, 'сшашша', '2023-04-29T15:46:29.393+02:00');
INSERT INTO public.messages (id, chat_id, sender_id, receiver_id, message_type, message_content, created_at) VALUES (1168, 98, 19, 20, 0, 'gegshs', '2023-04-29T15:52:37.484+02:00');
INSERT INTO public.messages (id, chat_id, sender_id, receiver_id, message_type, message_content, created_at) VALUES (1169, 99, 19, 20, 0, 'gegshs', '2023-04-29T15:52:37.484+02:00');
INSERT INTO public.messages (id, chat_id, sender_id, receiver_id, message_type, message_content, created_at) VALUES (1170, 98, 19, 20, 0, 'ggy', '2023-04-29T15:56:19.729+02:00');
INSERT INTO public.messages (id, chat_id, sender_id, receiver_id, message_type, message_content, created_at) VALUES (1171, 99, 19, 20, 0, 'ggy', '2023-04-29T15:56:19.729+02:00');
INSERT INTO public.messages (id, chat_id, sender_id, receiver_id, message_type, message_content, created_at) VALUES (1172, 98, 19, 20, 0, 'dhhdhd', '2023-04-29T16:11:59.109+02:00');
INSERT INTO public.messages (id, chat_id, sender_id, receiver_id, message_type, message_content, created_at) VALUES (1173, 99, 19, 20, 0, 'dhhdhd', '2023-04-29T16:11:59.109+02:00');
INSERT INTO public.messages (id, chat_id, sender_id, receiver_id, message_type, message_content, created_at) VALUES (1174, 98, 19, 20, 0, 'hxhxhxhx', '2023-04-29T19:54:15.386+02:00');
INSERT INTO public.messages (id, chat_id, sender_id, receiver_id, message_type, message_content, created_at) VALUES (1175, 99, 19, 20, 0, 'hxhxhxhx', '2023-04-29T19:54:15.386+02:00');
INSERT INTO public.messages (id, chat_id, sender_id, receiver_id, message_type, message_content, created_at) VALUES (1176, 98, 19, 20, 0, 'xhhxhxhx', '2023-04-29T20:21:39.956+02:00');
INSERT INTO public.messages (id, chat_id, sender_id, receiver_id, message_type, message_content, created_at) VALUES (1177, 99, 19, 20, 0, 'xhhxhxhx', '2023-04-29T20:21:39.956+02:00');
INSERT INTO public.messages (id, chat_id, sender_id, receiver_id, message_type, message_content, created_at) VALUES (1178, 98, 19, 20, 0, 'ufyd6d6', '2023-04-29T20:22:22.902+02:00');
INSERT INTO public.messages (id, chat_id, sender_id, receiver_id, message_type, message_content, created_at) VALUES (1179, 99, 19, 20, 0, 'ufyd6d6', '2023-04-29T20:22:22.902+02:00');
INSERT INTO public.messages (id, chat_id, sender_id, receiver_id, message_type, message_content, created_at) VALUES (1180, 98, 19, 20, 0, 'ycycufyffy', '2023-04-29T20:22:30.246+02:00');
INSERT INTO public.messages (id, chat_id, sender_id, receiver_id, message_type, message_content, created_at) VALUES (1181, 99, 19, 20, 0, 'ycycufyffy', '2023-04-29T20:22:30.246+02:00');
INSERT INTO public.messages (id, chat_id, sender_id, receiver_id, message_type, message_content, created_at) VALUES (1182, 98, 19, 20, 0, 'kekw', '2023-04-29T23:04:42.608+02:00');
INSERT INTO public.messages (id, chat_id, sender_id, receiver_id, message_type, message_content, created_at) VALUES (1183, 99, 19, 20, 0, 'kekw', '2023-04-29T23:04:42.608+02:00');


--
-- TOC entry 3434 (class 0 OID 49576)
-- Dependencies: 224
-- Data for Name: tokens; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.tokens (id, owner_id, token, updated_at) VALUES (6, 21, 'dKqenqPAQW2Ox5eVjlhhCI:APA91bE1RODL38zl0Oj5XMZHVmFXo5mlQNHG9yw3GBqDy-NjZy2nvGDbH0ySy083OiTiTe-niV3P8cVOvDo1-G-XU7Mr2azMd4nWfJxcqu_Lf2hVgQJaQ0_SOaNmOTrsS5OyQUhBM7dK', '2023-03-31 22:35:50.046406');
INSERT INTO public.tokens (id, owner_id, token, updated_at) VALUES (3, 18, 'dcmB9xwJQCyuT--hkkfJZg:APA91bFbUGV0q2iZCUnwgsNB0dUmHp2sJtRZQ23gh5nIw_MDpe0QVm17mbHtY0bGIsKP9rl4uXgzLfq0OyoW5gwcJLpoaeZPufuhc0e68TKKW0S9nO00hRrZ1vOzXM-Dn6A2AI0kxeT5', '2023-03-29 19:05:27.078628');
INSERT INTO public.tokens (id, owner_id, token, updated_at) VALUES (9, 20, 'f_azb5cFQ-G2tAodaDNtSo:APA91bGJ0wK5nfyn-A_6FmqaQ2HR8uAjCXcQGvlZm1XpVqVjrEO-iOHJwmHf9qH7MM5C4ddTJ6LHCkUKikhGFiddRbC8Zp4tWxlc1Q6HccY2bJWZezgmByHw232i1A_WbUxgI3znCEBQ', '2023-04-29 23:40:04.741753');
INSERT INTO public.tokens (id, owner_id, token, updated_at) VALUES (8, 19, 'djD4ZTgbQHG1VhUaXkSzVG:APA91bHK_XwtM4YB9O986k8lgyXCgirJO1_69-ZSlxaEQajGPfDOgAPbn_Awb3T7DnrfMGK7s-g0NVwTgl65SadZtks2P2mvMgqm9KVWS8iLs332R77d44a9NODzIbDmqMBVOgugLjm_', '2023-04-29 23:40:19.944333');
INSERT INTO public.tokens (id, owner_id, token, updated_at) VALUES (7, 18, 'frYMh-kSQGOX8I0gjw1eDo:APA91bGjEARYZkeLuvMFln0BhnxvxHjjhnL_2dKHfVw3OBWnzhw1DumEXQDLI5pd_6t4qbtojsgsBtB9br6KWbp5B9okUVgWDxYAeNLeZmAuk0-AtYQ-gEuAfrUQlys8zPqfu8Yh9e5u', '2023-04-01 20:31:53.920441');
INSERT INTO public.tokens (id, owner_id, token, updated_at) VALUES (10, 17, 'fNirB65RR6ubj3Aog8OqWD:APA91bGV67abDSzsGIr4Jv4gGLDzgZ6SSQZ2YwiI7HL0Y4qVal8-hcAzJTiTYmtVFNTfH9udjj_jSDLDaW2uXMgKfwkEv2wF1C-wpG4K8qzr2yhb7vdIO_P5_U8-Krny2QhgwacEHXXc', '2023-04-29 20:59:07.243403');


--
-- TOC entry 3437 (class 0 OID 57843)
-- Dependencies: 227
-- Data for Name: user_contacts; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.user_contacts (id, user_id, contact_id) VALUES (1, 19, 20);
INSERT INTO public.user_contacts (id, user_id, contact_id) VALUES (2, 17, 20);
INSERT INTO public.user_contacts (id, user_id, contact_id) VALUES (3, 17, 19);


--
-- TOC entry 3461 (class 0 OID 0)
-- Dependencies: 215
-- Name: accounts_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.accounts_id_seq', 22, true);


--
-- TOC entry 3462 (class 0 OID 0)
-- Dependencies: 217
-- Name: auth_keys_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.auth_keys_id_seq', 116, true);


--
-- TOC entry 3463 (class 0 OID 0)
-- Dependencies: 219
-- Name: chats_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.chats_id_seq', 103, true);


--
-- TOC entry 3464 (class 0 OID 0)
-- Dependencies: 221
-- Name: files_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.files_id_seq', 32, true);


--
-- TOC entry 3465 (class 0 OID 0)
-- Dependencies: 228
-- Name: group_chats_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.group_chats_id_seq', 92, true);


--
-- TOC entry 3466 (class 0 OID 0)
-- Dependencies: 232
-- Name: group_messages_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.group_messages_id_seq', 16, true);


--
-- TOC entry 3467 (class 0 OID 0)
-- Dependencies: 230
-- Name: groups_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.groups_id_seq', 294, true);


--
-- TOC entry 3468 (class 0 OID 0)
-- Dependencies: 223
-- Name: messages_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.messages_id_seq', 1183, true);


--
-- TOC entry 3469 (class 0 OID 0)
-- Dependencies: 225
-- Name: tokens_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.tokens_id_seq', 10, true);


--
-- TOC entry 3470 (class 0 OID 0)
-- Dependencies: 226
-- Name: user_contacts_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.user_contacts_id_seq', 3, true);


--
-- TOC entry 3235 (class 2606 OID 49587)
-- Name: accounts accounts_image_id_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.accounts
    ADD CONSTRAINT accounts_image_id_key UNIQUE (image_id);


--
-- TOC entry 3238 (class 2606 OID 49589)
-- Name: accounts accounts_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.accounts
    ADD CONSTRAINT accounts_pk PRIMARY KEY (id);


--
-- TOC entry 3242 (class 2606 OID 49591)
-- Name: auth_keys auth_keys_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.auth_keys
    ADD CONSTRAINT auth_keys_pk PRIMARY KEY (id);


--
-- TOC entry 3245 (class 2606 OID 49593)
-- Name: chats chats_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.chats
    ADD CONSTRAINT chats_pk PRIMARY KEY (id);


--
-- TOC entry 3247 (class 2606 OID 49595)
-- Name: files files_name_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.files
    ADD CONSTRAINT files_name_key UNIQUE (name);


--
-- TOC entry 3249 (class 2606 OID 49597)
-- Name: files files_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.files
    ADD CONSTRAINT files_pkey PRIMARY KEY (id);


--
-- TOC entry 3262 (class 2606 OID 57868)
-- Name: group_chats group_chats_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.group_chats
    ADD CONSTRAINT group_chats_pk PRIMARY KEY (id);


--
-- TOC entry 3268 (class 2606 OID 57917)
-- Name: group_messages group_messages_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.group_messages
    ADD CONSTRAINT group_messages_pk PRIMARY KEY (id);


--
-- TOC entry 3265 (class 2606 OID 57906)
-- Name: groups groups_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.groups
    ADD CONSTRAINT groups_pk PRIMARY KEY (id);


--
-- TOC entry 3252 (class 2606 OID 49599)
-- Name: messages messages_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.messages
    ADD CONSTRAINT messages_pk PRIMARY KEY (id);


--
-- TOC entry 3254 (class 2606 OID 49601)
-- Name: tokens tokens_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tokens
    ADD CONSTRAINT tokens_pkey PRIMARY KEY (id);


--
-- TOC entry 3256 (class 2606 OID 49603)
-- Name: tokens tokens_token_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tokens
    ADD CONSTRAINT tokens_token_key UNIQUE (token);


--
-- TOC entry 3259 (class 2606 OID 57859)
-- Name: user_contacts user_contacts_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_contacts
    ADD CONSTRAINT user_contacts_pk PRIMARY KEY (id);


--
-- TOC entry 3233 (class 1259 OID 49604)
-- Name: accounts_id_uindex; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX accounts_id_uindex ON public.accounts USING btree (id);


--
-- TOC entry 3236 (class 1259 OID 49605)
-- Name: accounts_mail_uindex; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX accounts_mail_uindex ON public.accounts USING btree (mail);


--
-- TOC entry 3239 (class 1259 OID 49606)
-- Name: accounts_username_uindex; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX accounts_username_uindex ON public.accounts USING btree (username);


--
-- TOC entry 3240 (class 1259 OID 49607)
-- Name: auth_keys_id_uindex; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX auth_keys_id_uindex ON public.auth_keys USING btree (id);


--
-- TOC entry 3243 (class 1259 OID 49608)
-- Name: chats_id_uindex; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX chats_id_uindex ON public.chats USING btree (id);


--
-- TOC entry 3260 (class 1259 OID 57866)
-- Name: group_chats_id_uindex; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX group_chats_id_uindex ON public.group_chats USING btree (id);


--
-- TOC entry 3266 (class 1259 OID 57915)
-- Name: group_messages_id_uindex; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX group_messages_id_uindex ON public.group_messages USING btree (id);


--
-- TOC entry 3263 (class 1259 OID 57904)
-- Name: groups_id_uindex; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX groups_id_uindex ON public.groups USING btree (id);


--
-- TOC entry 3250 (class 1259 OID 49609)
-- Name: messages_id_uindex; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX messages_id_uindex ON public.messages USING btree (id);


--
-- TOC entry 3257 (class 1259 OID 57857)
-- Name: user_contacts_id_uindex; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX user_contacts_id_uindex ON public.user_contacts USING btree (id);


--
-- TOC entry 3269 (class 2606 OID 49610)
-- Name: accounts accounts_files__fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.accounts
    ADD CONSTRAINT accounts_files__fk FOREIGN KEY (image_id) REFERENCES public.files(id);


--
-- TOC entry 3272 (class 2606 OID 49615)
-- Name: messages chatid; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.messages
    ADD CONSTRAINT chatid FOREIGN KEY (chat_id) REFERENCES public.chats(id);


--
-- TOC entry 3280 (class 2606 OID 57918)
-- Name: group_messages group_messages_accounts_id_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.group_messages
    ADD CONSTRAINT group_messages_accounts_id_fk FOREIGN KEY (sender_id) REFERENCES public.accounts(id);


--
-- TOC entry 3281 (class 2606 OID 57923)
-- Name: group_messages group_messages_group_chats_id_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.group_messages
    ADD CONSTRAINT group_messages_group_chats_id_fk FOREIGN KEY (group_id) REFERENCES public.group_chats(id);


--
-- TOC entry 3278 (class 2606 OID 57894)
-- Name: groups groups_accounts_id_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.groups
    ADD CONSTRAINT groups_accounts_id_fk FOREIGN KEY (user_id) REFERENCES public.accounts(id);


--
-- TOC entry 3279 (class 2606 OID 57899)
-- Name: groups groups_group_chats_id_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.groups
    ADD CONSTRAINT groups_group_chats_id_fk FOREIGN KEY (group_id) REFERENCES public.group_chats(id);


--
-- TOC entry 3270 (class 2606 OID 49620)
-- Name: chats owner; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.chats
    ADD CONSTRAINT owner FOREIGN KEY (owner_id) REFERENCES public.accounts(id);


--
-- TOC entry 3275 (class 2606 OID 49625)
-- Name: tokens owner; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tokens
    ADD CONSTRAINT owner FOREIGN KEY (owner_id) REFERENCES public.accounts(id);


--
-- TOC entry 3271 (class 2606 OID 49630)
-- Name: chats receiver; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.chats
    ADD CONSTRAINT receiver FOREIGN KEY (receiver_id) REFERENCES public.accounts(id);


--
-- TOC entry 3273 (class 2606 OID 49635)
-- Name: messages receiverid; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.messages
    ADD CONSTRAINT receiverid FOREIGN KEY (receiver_id) REFERENCES public.accounts(id);


--
-- TOC entry 3274 (class 2606 OID 49640)
-- Name: messages senderid; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.messages
    ADD CONSTRAINT senderid FOREIGN KEY (sender_id) REFERENCES public.accounts(id);


--
-- TOC entry 3276 (class 2606 OID 57847)
-- Name: user_contacts user_contacts_accounts_id_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_contacts
    ADD CONSTRAINT user_contacts_accounts_id_fk FOREIGN KEY (user_id) REFERENCES public.accounts(id);


--
-- TOC entry 3277 (class 2606 OID 57852)
-- Name: user_contacts user_contacts_accounts_id_fk_2; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_contacts
    ADD CONSTRAINT user_contacts_accounts_id_fk_2 FOREIGN KEY (contact_id) REFERENCES public.accounts(id);


-- Completed on 2023-04-29 23:45:46

--
-- PostgreSQL database dump complete
--

