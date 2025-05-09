-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: db
-- Creato il: Mag 09, 2025 alle 13:13
-- Versione del server: 11.4.3-MariaDB-ubu2404
-- Versione PHP: 8.2.22

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `gametalk_db`
--

-- --------------------------------------------------------

--
-- Struttura della tabella `categories`
--

CREATE TABLE `categories` (
  `name` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dump dei dati per la tabella `categories`
--

INSERT INTO `categories` (`name`) VALUES
('Announcements'),
('Bugs'),
('General'),
('Guides'),
('Help'),
('Memes'),
('Reports'),
('Welcome');

-- --------------------------------------------------------

--
-- Struttura della tabella `comments`
--

CREATE TABLE `comments` (
  `id` bigint(20) NOT NULL,
  `thread_id` bigint(20) NOT NULL,
  `username` varchar(24) DEFAULT NULL,
  `body` text NOT NULL,
  `votes` int(11) NOT NULL DEFAULT 0,
  `creation_date` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dump dei dati per la tabella `comments`
--

INSERT INTO `comments` (`id`, `thread_id`, `username`, `body`, `votes`, `creation_date`) VALUES
(43, 55, 'LightHealer', 'Voglio registrarmi al torneo!', 0, '2025-04-26'),
(44, 51, 'LightHealer', 'La puoi trovare al Forte Di Farron', 1, '2025-05-02'),
(45, 53, 'LightHealer', 'io uso 80x80', 0, '2025-05-02'),
(47, 59, 'AlessioSica', 'Aggiungetemi su discord comunque: @Lexi115', 0, '2025-05-02'),
(48, 60, 'AlessioSica', 'xd', 0, '2025-05-02'),
(50, 58, 'AlessioSica', 'molto bello', 0, '2025-05-07'),
(59, 61, NULL, 'cc', 0, '2025-05-08'),
(68, 51, 'bingaDinga', 'test', 0, '2025-05-09'),
(69, 55, 'Moderator1000!', 'Carino', 0, '2025-05-09');

-- --------------------------------------------------------

--
-- Struttura della tabella `roles`
--

CREATE TABLE `roles` (
  `name` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dump dei dati per la tabella `roles`
--

INSERT INTO `roles` (`name`) VALUES
('Member'),
('Moderator');

-- --------------------------------------------------------

--
-- Struttura della tabella `threads`
--

CREATE TABLE `threads` (
  `id` bigint(20) NOT NULL,
  `username` varchar(24) DEFAULT NULL,
  `title` varchar(100) NOT NULL,
  `body` text NOT NULL,
  `votes` int(11) NOT NULL DEFAULT 0,
  `archived` tinyint(1) NOT NULL DEFAULT 0,
  `category` varchar(20) DEFAULT NULL,
  `creation_date` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dump dei dati per la tabella `threads`
--

INSERT INTO `threads` (`id`, `username`, `title`, `body`, `votes`, `archived`, `category`, `creation_date`) VALUES
(51, 'DarkSlayer', 'Titanite su Dark Souls 3', '<p>Ragazzi potete indicarmi dove trovare pezzi di titanite? non riesco a trovarne.</p><p>Ecco una foto dell\'oggetto che sto cercando</p><p><img src=\"https://darksouls3.wiki.fextralife.com/file/Dark-Souls-3/TitaniteShard.png\" style=\"width: 190px;\"></p><p>Grazie in anticipo</p>', 1, 0, 'Help', '2025-04-25'),
(52, 'DarkSlayer', 'Shared progression lag su Lords of the Fallen', '<p>Salve ho riscontrato un problema di desync quando provo a giocare con un mio amico a Lords Of The Fallen, dopo la parte paludosa il giocatore non host soffre di severi problemi di desync e lag generale che rende l\'esperienza ingiocabile, qualcuno ha riscontrato lo stesso problema?</p>', 0, 0, 'Bugs', '2025-04-25'),
(53, 'DarkSlayer', 'Area tavoletta grafica su osu!', '<p>Sono curioso di sapere <b>quale area utilizzano i giocatori nella community</b>, questa è la mia area attualmente <font color=\"#00ffff\">60x35</font></p><p><img style=\"width: 1171px;\" src=\"https://i.imgur.com/liuywcS.png\"><br></p>', 0, 0, 'General', '2025-04-25'),
(55, 'DarkSlayer', 'Torneo di Bedwars (4v4)', '<p>Terrei ad annunciare un torneo di bedwars 4v4 che si terrà sul server Hypixel i premi sono i seguenti:</p><p>Gli iscritti al torneo che possiedono il rank <font color=\"#00ffff\" style=\"font-weight: bold;\">MVP</font><font color=\"#ff0000\" style=\"font-weight: bold;\">+&nbsp;</font>/&nbsp;<font color=\"#ff9c00\" style=\"font-weight: bold;\">MVP</font><font color=\"#ff0000\" style=\"font-weight: bold;\">++</font> avranno diritto ad un bonus di <font color=\"#00ff00\">50$</font> qualora si classificassero nella top 3 e un ulteriore premio di partecipazione di <font color=\"#00ff00\">10$</font></p><table class=\"table table-bordered\"><tbody><tr><td style=\"text-align: center; \">Posizione</td><td>Premio</td></tr><tr><td style=\"text-align: center; \"><font color=\"#efc631\"><b>1</b></font></td><td><p>Rank <font color=\"#ff9c00\" style=\"\">MVP</font><font color=\"#ff0000\" style=\"\">++</font> sul server Hypixel per <b>1 anno intero</b></p><p><font color=\"#00ff00\"><b>300$</b></font> <u>Divisi</u> tra i membri del team vincente</p></td></tr><tr><td style=\"text-align: center; \"><font color=\"#cec6ce\"><b>2</b></font></td><td><p>Rank <font color=\"#00ffff\" style=\"\">MVP</font>&nbsp;sul server Hypixel</p><p><font color=\"#00ff00\"><span style=\"font-weight: bolder;\">100$</span></font>&nbsp;<u>Divisi</u>&nbsp;tra i membri del team vincente</p></td></tr><tr><td style=\"text-align: center; \"><font color=\"#b56308\"><b>3</b></font></td><td><p>Rank <font color=\"#00ff00\">VIP</font> sul server Hypixel</p><p><font color=\"#00ff00\"><b>40$</b></font>&nbsp;<u>Divisi</u>&nbsp;tra i membri del team vincente</p></td></tr></tbody></table><p>Le iscrizioni sono aperte oggi e termineranno tra un mese, affrettatevi a registrarvi!</p><p>I match verranno <b>streammati sul canale ufficiale di Hypixel</b> su <b style=\"\"><font color=\"#9c00ff\">Twitch</font></b></p>', 4, 0, 'Announcements', '2025-04-25'),
(57, 'LightHealer', 'Easter egg revelations', '<p>Ho trovato un link di una guida utile per completare l\'easter egg di revelations su <font color=\"#ff9c00\">\"Call Of Duty\"</font></p><p><a href=\"https://www.uagna.it/videogiochi/guide-videogiochi/revelations-guida-completa-easter-egg-74850\" target=\"_blank\"><b><font color=\"#00ffff\">Link alla guida</font></b></a><br></p>', 0, 0, 'Guides', '2025-05-02'),
(58, 'LightHealer', 'Oblivion Remastered', '<p>Ragazzi che ne pensate del remaster di <b>Oblivion</b>? Già sento tante belle parole a riguardo, a me interessa tanto ma non sono sicuro se ne valga i soldi spesi, cosa dite?</p><p><img style=\"width: 616px;\" src=\"https://gaming-cdn.com/images/products/18367/616x353/the-elder-scrolls-iv-oblivion-remastered-pc-gioco-steam-cover.jpg?v=1745392020\"><br></p>', 3, 0, 'General', '2025-05-02'),
(59, 'AlessioSica', 'Ciao ragazzi', '<p>Ciao a tutti ragazzi sono nuovo qui. Come va? :DDD<br></p><p><img style=\"width: 165px;\" src=\"https://media.tenor.com/-ufrqpl5cp0AAAAM/test.gif\"><br></p>', 0, 0, 'Welcome', '2025-05-02'),
(60, 'AlessioSica', 'PS5 non ha giochi lol', '<p><img style=\"width: 708px;\" src=\"https://images.hothardware.com/contentimages/newsitem/61737/content/ps5-has-no-games.jpg\"><br></p>', 0, 0, 'Memes', '2025-05-02'),
(61, 'AlessioSica', 'fun fact sui giapponesi', '<p>Il 20% dei giovani giapponesi spende così tanto sui <b>gacha</b> che non hanno abbastanza soldi per le spese quotidiane. </p><p><font color=\"#00ff00\"><b><span style=\"font-size: 24px;\">SAD</span></b></font></p><p><img style=\"width: 720px;\" src=\"https://www.yugatech.com/wp-content/uploads/2019/01/fgo.jpg\"><br></p>', 0, 0, 'Memes', '2025-05-02'),
(66, 'bingaDinga', 'mio titolo', 'mio corpo', 0, 0, 'Announcements', '2025-05-06'),
(67, 'bingaDinga', 'mio titolo', 'mio corpo', 0, 0, 'Announcements', '2025-05-06'),
(80, 'Moderator1000!', 'Come moddare una wii - TUTORIAL', '<p data-start=\"232\" data-end=\"360\" class=\"\"><strong data-start=\"232\" data-end=\"246\">Disclaimer</strong>: Questo tutorial è solo a scopo informativo. Scaricare giochi che non possiedi è illegale. Procedi a tuo rischio.</p><hr data-start=\"362\" data-end=\"365\" class=\"\"><p data-start=\"367\" data-end=\"558\" class=\"\"><strong data-start=\"367\" data-end=\"405\">1. Controlla la versione della Wii</strong><br data-start=\"405\" data-end=\"408\">\nVai su <em data-start=\"415\" data-end=\"433\">Impostazioni Wii</em> e guarda in alto a destra: ti serve sapere se hai, ad esempio, 4.3E, 4.1U, ecc. È importante per scegliere il metodo giusto.</p><hr data-start=\"560\" data-end=\"563\" class=\"\"><p data-start=\"565\" data-end=\"595\" class=\"\"><strong data-start=\"565\" data-end=\"593\">2. Prepara una scheda SD</strong></p><ul data-start=\"596\" data-end=\"725\">\n<li data-start=\"596\" data-end=\"631\" class=\"\">\n<p data-start=\"598\" data-end=\"631\" class=\"\">Formatta la scheda SD in FAT32.</p>\n</li>\n<li data-start=\"632\" data-end=\"683\" class=\"\">\n<p data-start=\"634\" data-end=\"683\" class=\"\">Consiglio: usa una scheda da 2GB, massimo 32GB.</p>\n</li>\n<li data-start=\"684\" data-end=\"725\" class=\"\">\n<p data-start=\"686\" data-end=\"725\" class=\"\">Non usare SDHC per i metodi più vecchi.</p>\n</li>\n</ul><hr data-start=\"727\" data-end=\"730\" class=\"\"><p data-start=\"732\" data-end=\"778\" class=\"\"><strong data-start=\"732\" data-end=\"776\">3. Scarica LetterBomb (solo per Wii 4.3)</strong></p><ul data-start=\"779\" data-end=\"1011\">\n<li data-start=\"779\" data-end=\"838\" class=\"\">\n<p data-start=\"781\" data-end=\"838\" class=\"\">Vai su: <a data-start=\"789\" data-end=\"836\" rel=\"noopener\" target=\"_new\" class=\"cursor-pointer\">please.hackmii.com</a></p>\n</li>\n<li data-start=\"839\" data-end=\"921\" class=\"\">\n<p data-start=\"841\" data-end=\"921\" class=\"\">Inserisci il tuo MAC Address (lo trovi nelle impostazioni Internet della Wii).</p>\n</li>\n<li data-start=\"922\" data-end=\"948\" class=\"\">\n<p data-start=\"924\" data-end=\"948\" class=\"\">Scegli la tua regione.</p>\n</li>\n<li data-start=\"949\" data-end=\"1011\" class=\"\">\n<p data-start=\"951\" data-end=\"1011\" class=\"\">Riceverai un file ZIP: estrai tutto sulla root della tua SD.</p>\n</li>\n</ul><hr data-start=\"1013\" data-end=\"1016\" class=\"\"><p data-start=\"1018\" data-end=\"1052\" class=\"\"><strong data-start=\"1018\" data-end=\"1050\">4. Inserisci la SD nella Wii</strong></p><ul data-start=\"1053\" data-end=\"1211\">\n<li data-start=\"1053\" data-end=\"1119\" class=\"\">\n<p data-start=\"1055\" data-end=\"1119\" class=\"\">Vai sulla bacheca Wii, cerca il messaggio rosso con una bomba.</p>\n</li>\n<li data-start=\"1120\" data-end=\"1162\" class=\"\">\n<p data-start=\"1122\" data-end=\"1162\" class=\"\">Aprilo e partirà l’exploit LetterBomb.</p>\n</li>\n<li data-start=\"1163\" data-end=\"1211\" class=\"\">\n<p data-start=\"1165\" data-end=\"1211\" class=\"\">Dopo un po’, si aprirà l’installer di HackMii.</p>\n</li>\n</ul><hr data-start=\"1213\" data-end=\"1216\" class=\"\"><p data-start=\"1218\" data-end=\"1254\" class=\"\"><strong data-start=\"1218\" data-end=\"1252\">5. Installa l’Homebrew Channel</strong></p><ul data-start=\"1255\" data-end=\"1432\">\n<li data-start=\"1255\" data-end=\"1321\" class=\"\">\n<p data-start=\"1257\" data-end=\"1321\" class=\"\">Nell’HackMii Installer, scegli “Install the Homebrew Channel”.</p>\n</li>\n<li data-start=\"1322\" data-end=\"1381\" class=\"\">\n<p data-start=\"1324\" data-end=\"1381\" class=\"\">Se vuoi, puoi anche installare BootMii (se supportato).</p>\n</li>\n<li data-start=\"1382\" data-end=\"1432\" class=\"\">\n<p data-start=\"1384\" data-end=\"1432\" class=\"\">Riavvia: ora avrai l’Homebrew Channel sulla Wii.</p>\n</li>\n</ul><hr data-start=\"1434\" data-end=\"1437\" class=\"\"><p data-start=\"1439\" data-end=\"1489\" class=\"\"><strong data-start=\"1439\" data-end=\"1487\">6. Installa i cIOS (per i backup dei giochi)</strong></p><ul data-start=\"1490\" data-end=\"1699\">\n<li data-start=\"1490\" data-end=\"1536\" class=\"\">\n<p data-start=\"1492\" data-end=\"1536\" class=\"\">Scarica il programma “d2x cIOS Installer”.</p>\n</li>\n<li data-start=\"1537\" data-end=\"1580\" class=\"\">\n<p data-start=\"1539\" data-end=\"1580\" class=\"\">Copialo nella cartella <code data-start=\"1562\" data-end=\"1568\">apps</code> della SD.</p>\n</li>\n<li data-start=\"1581\" data-end=\"1616\" class=\"\">\n<p data-start=\"1583\" data-end=\"1616\" class=\"\">Avvialo dalla Homebrew Channel.</p>\n</li>\n<li data-start=\"1617\" data-end=\"1699\" class=\"\">\n<p data-start=\"1619\" data-end=\"1699\" class=\"\">Segui le istruzioni (di solito si installano i cIOS 249 e 250 con base 56 e 57).</p>\n</li>\n</ul><hr data-start=\"1701\" data-end=\"1704\" class=\"\"><p data-start=\"1706\" data-end=\"1730\" class=\"\"><strong data-start=\"1706\" data-end=\"1728\">7. Metti un loader</strong></p><ul data-start=\"1731\" data-end=\"1903\">\n<li data-start=\"1731\" data-end=\"1767\" class=\"\">\n<p data-start=\"1733\" data-end=\"1767\" class=\"\">Scarica USB Loader GX o WiiFlow.</p>\n</li>\n<li data-start=\"1768\" data-end=\"1806\" class=\"\">\n<p data-start=\"1770\" data-end=\"1806\" class=\"\">Inseriscilo nella cartella <code data-start=\"1797\" data-end=\"1803\">apps</code>.</p>\n</li>\n<li data-start=\"1807\" data-end=\"1903\" class=\"\">\n<p data-start=\"1809\" data-end=\"1903\" class=\"\">Ti servirà un hard disk USB o una chiavetta con i giochi nel formato giusto (cartella <code data-start=\"1895\" data-end=\"1901\">wbfs</code>).</p>\n</li>\n</ul><hr data-start=\"1905\" data-end=\"1908\" class=\"\"><p data-start=\"1910\" data-end=\"1933\" class=\"\"><strong data-start=\"1910\" data-end=\"1931\">8. Avvia i giochi</strong></p><p>\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n</p><ul data-start=\"1934\" data-end=\"2072\">\n<li data-start=\"1934\" data-end=\"1958\" class=\"\">\n<p data-start=\"1936\" data-end=\"1958\" class=\"\">Collega l’hard disk.</p>\n</li>\n<li data-start=\"1959\" data-end=\"2006\" class=\"\">\n<p data-start=\"1961\" data-end=\"2006\" class=\"\">Avvia USB Loader GX dalla Homebrew Channel.</p></li>\n<li data-start=\"2007\" data-end=\"2072\" class=\"\">\n<p data-start=\"2009\" data-end=\"2072\" class=\"\">Se tutto è stato fatto bene, vedrai i giochi e potrai avviarli.</p></li></ul>', 0, 1, 'Guides', '2025-05-06');

-- --------------------------------------------------------

--
-- Struttura della tabella `users`
--

CREATE TABLE `users` (
  `username` varchar(24) NOT NULL,
  `password` char(64) NOT NULL,
  `creation_date` date NOT NULL,
  `banned` tinyint(1) NOT NULL DEFAULT 0,
  `role` varchar(20) NOT NULL,
  `auth_token` char(64) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dump dei dati per la tabella `users`
--

INSERT INTO `users` (`username`, `password`, `creation_date`, `banned`, `role`, `auth_token`) VALUES
('AlessioSica', '$2a$12$MojvuoEbkkoZ7QzMKjzJJ.7fEqxuv0R4RuNl1SjUyf3DeNPji6YVy', '2025-05-02', 0, 'Member', '788b8749498dff94b40b9a52cb14dc703e44a077cd931a5401b3b5e275006265'),
('AlphaMaster', '280870c9eff2b07b4b1fb79fee8de7767304d2ebc7bdfb0a7fb07965bb50974e', '2020-08-19', 0, 'Member', NULL),
('ApexLegends', '04138ec675e0b4e5df579f53fe7adbb9455cbd095f0ee39fcd190b17125ba015', '2021-10-14', 0, 'Member', NULL),
('bingaDinga', '$2a$12$8KAov7Qwxi5YEpxypQ1.ZuvRUGO1u7qq2FmK810jEQaUVPuHNxxFe', '2025-03-15', 0, 'Member', 'f5c731a04803c226b7d0852c0d66bab9b174c5f0577da3783037458efd86dfe7'),
('BlazeX', '763dc0638379c7a7283df2387d8216add19962db95a6ad480f00aa16640f933e', '2021-12-17', 0, 'Member', NULL),
('CaptainHunter', '08cc409fccea052d56440a3e9169eb8c846588aaf6a562d1595c0235d15525d6', '2021-05-18', 1, 'Member', NULL),
('CarmeloCapp', '0a91ebd0814fb49ccf3885e4348804a243dcd89566d541e6a5803bb67a93ecfa', '2025-03-03', 0, 'Moderator', '66b256cf5ca8d9cd98176116c28b6fb219fdd58180bbabf7aaf2601092146a60'),
('ciaoCIAO2003', '$2a$12$W9qN7NH4JJh6Q.8sQ/tJyOdjC486I.Q32tywNUiUrcac4bjGkQYKm', '2025-03-21', 0, 'Moderator', '38ebdcf96aaeb89bfe4b1be1a35944e9fce32da6ce10e8abc072e83176ac9919'),
('ciaoCiao89', '$2a$12$b6Uo6ao/EB3CfdQs9rpNpOxBcCYdwsA9HRZ0SA2Hj/CK7uc8CosLa', '2025-03-15', 1, 'Member', NULL),
('ClashMaster', 'fcb83cd8f588a5ee8645febd8b26ab552ef0c20e8b008da85c9a6820c0a86915', '2022-03-01', 0, 'Member', NULL),
('CobraGamer', '291bba476354db0d4f68833362d2595ad91a556fb3806ee579106b861b339415', '2022-05-12', 0, 'Member', NULL),
('CyberLuca', '60f8ad0203ffd8d62f3c22da86e00d5849a252336c2ad5f7bbe30cf9ac22ebf1', '2022-10-11', 0, 'Member', NULL),
('CyberNinja', 'af4a9124ba5a487957e9b19cc1de0c402271bee0fb14c406225d713917d7262c', '2022-02-17', 0, 'Member', NULL),
('DarkSlayer', '$2a$12$.H1bqTta9FRC6Ch.DVelEOXtn9c/Q13NpUtqSinlErEZzfpoayioK', '2025-04-25', 0, 'Member', '2bd559406c5b74c3475a6c6bb6c8fe329fdc98eddda5d5049a9b846f5dc90aaf'),
('DragonMaster', '1a40fa88f16273fa9d8be7ae7c762525713c672a7f402836517faccd2e0b5db9', '2021-03-19', 0, 'Member', NULL),
('EliteGamer', '95c7d8306878be8862a2bcbba7e67627c3343fed666fca1e2a96c5a76d7d9299', '2020-02-15', 0, 'Member', NULL),
('FuryPlays', '081aca486da16444f6c327ba311bfdba97b8c4f0a355940284e0bb3edf3a3032', '2020-09-14', 1, 'Member', NULL),
('GameKnight', '9f9067baa5b751017d9a08dbab70aca315929f05b683816ddfa6f2254b34152c', '2019-04-17', 0, 'Member', NULL),
('GamerAlessio', 'a109e36947ad56de1dca1cc49f0ef8ac9ad9a7b1aa0df41fb3c4cb73c1ff01ea', '2023-05-14', 0, 'Member', NULL),
('GhostNinja', '49b2cb2c66a56042785640838f02c46e07adf0b99580cb108bbc43541681da78', '2021-06-04', 0, 'Member', NULL),
('GhostXPlayer', 'c6dfc53debb071a4e7a07c33377c07d8ef1fd512b85a141b299d1e856898628c', '2022-03-15', 0, 'Member', NULL),
('HyperPlays', '582f325c3bf05be84c171549c48c02137603fa6cb550aa34595dafd4137e1941', '2021-03-22', 0, 'Member', NULL),
('IronFist', '0c9ac04c42f3d5f2fac1d17216b202b503249ca57e1e72db824fca9548ebab60', '2022-01-06', 0, 'Member', NULL),
('IronPlays', '264e2c285ffe85fe8a04a4648d26af9128283a0cac968926f753d1b1b4b015e5', '2021-02-12', 0, 'Member', NULL),
('LightHealer', '$2a$12$fXgj5hILMIBuu3hhZiC3NezeUYe.HIJsRX340jZsiPqRaY7d4GQhW', '2025-04-26', 0, 'Moderator', '3d078f27908ffaa2ee06baed45a645eb8bf168b888b91f1c396db9d7ba764803'),
('LucaPlays', 'a2ef4aab9f5ab9cb02d1753b9a6ae33112b40e2b5d6c94aee3aa23d7f0b03aa9', '2021-07-22', 0, 'Member', NULL),
('MasterAce', '909597f57c40394d3844aef9f5a97defb9751a182be0d8561b8f8cb0140ea952', '2021-08-03', 0, 'Member', NULL),
('MaxPlays', '797d672dd9b4a0135cb2a038b81a200288ae3bd7b682e50c509b173890c55b78', '2021-09-06', 0, 'Member', NULL),
('MaxPower', '74a54e006cae9e25afefce98aacab80ab2bdd9dce97b11e696eaca0344d0b065', '2020-04-28', 0, 'Member', NULL),
('MightyRocco', '64dac4dc3995d3707fd124cc4fdaa43b9f2e0a02f171064ca34b357d50009fac', '2020-12-25', 0, 'Member', NULL),
('Moderator1000!', '$2a$12$J9mEG1CubrpFU8AVBdh.euaqp4kMOcxJ/s6p4jaTdpmZwRZxcSyzq', '2025-05-09', 0, 'Moderator', '42b47ce253661d3884f6b004536d747c1f01369a2168a7a64403c9d3179d56f0'),
('NicolaTheKing', 'baeb143b4f9619d6e372ec42089657aaf8ac1ed0cb02bb830ff815ee3c0add79', '2020-08-30', 0, 'Member', NULL),
('NightHawk', '7fcec9109f8ed80d9322e1a96009671d95cb1c319fbe8ec317abdf3ded4edb23', '2020-11-08', 0, 'Member', NULL),
('NinjaMike', '5c1ba440f65726eb9bb8d737230fcb44c7286ffcc84903115f0a316dbf705c84', '2020-11-23', 0, 'Member', NULL),
('PhoenixGamer', 'bf536bba7b97bea730cd3bf317ff9dbf945fa8fa2a54c490e6ec9bdc4bab6a9c', '2021-02-23', 0, 'Member', NULL),
('PixelMaster', '6a82426fd32fc6982115b61b2267040626206919851d728941468b4c96cdd521', '2021-09-13', 0, 'Member', NULL),
('PlayerOne', 'c957a5cd33151c98733827a7dd38e0f29ca9697886d5a5d4bfb671dc20c5f22c', '2022-02-07', 0, 'Member', NULL),
('RageGamer', '8b42c2f8caa36b728eabc2c85f21aef26762add17e867442a2431c1ff4964693', '2021-05-05', 0, 'Member', NULL),
('RogueX', 'd957a7c2b59b12ac46dd2ce5938a63308635aa3bb06e5d7d07c5cdc9f870866a', '2021-07-15', 0, 'Member', NULL),
('ShadowPlays', '22933b1217ac5fe89aa50a1703e11382fc89de21724d93e9878410780e804398', '2022-01-01', 0, 'Member', NULL),
('ShadowX', 'dc73d3093f82eea7720356abe3f9fb9e58821f7e55d355dfc211a6569e0e76c1', '2021-11-12', 0, 'Member', NULL),
('SkyRiderX', 'c3980813e7121c84b37442b897358615508697b66516c2c7518a767c9722234d', '2021-07-23', 0, 'Member', NULL),
('SpeedsterX', 'f9fe76f1652f3e42466e3d6958e9aa2de5b133d0a96e6df30e5502f180a0de58', '2019-12-22', 0, 'Member', NULL),
('StealthMaster', '004ebf9ea10cac70500befd8035810127811bb7b9f9d04f28e4dbb290306f43f', '2021-04-04', 0, 'Member', NULL),
('StormGamer', '071661e4e3b9aa9023e6d09a4a286cd3f8aa2e7a33fd0a6932f0da619d5a72bd', '2022-09-03', 0, 'Member', NULL),
('StormXPlayer', 'f366bd3eaf8662b85ec70f40f84aca9e217df5ec7cf9ffa6b05d4f7a9b5287f5', '2020-05-23', 0, 'Member', NULL),
('ThunderX', '0e21b460bb453e704ae7a36bb6d44aabf92dcf72d00630bbf781d1a66685c113', '2022-07-15', 0, 'Member', NULL),
('TornadoPlayer', '6cf473c7206ed4a10a7d43d2afebb9a509d22137a5115de6e3a56956f974d7a8', '2022-04-10', 0, 'Member', NULL),
('TurboGamer', 'a74753784a37c1138532484dc70d8caa5f526e12be78a82ebbab24e657595155', '2020-06-11', 0, 'Member', NULL),
('UltraGamer', 'f3bec15f95ccc104cf197834eacc4c71bf57c73b9278a58c8c4205a8abb98d52', '2022-06-17', 0, 'Member', NULL),
('UtenteGenerico1000!', '$2a$12$xwctZdq9j17sR5DYvnOENe4gIhJc87daUya4xpxcycjNYxwvrALvG', '2025-05-09', 0, 'Member', 'c157a90db7ae15d78463906a11b6340ab1e2b1bbc34f8d385a33e1dd2d9a7d4e'),
('ViperElite', 'cfd574e4ee5bf424e631c81e4ece6ea753160efd86d4ae62217be21ae89df00b', '2021-10-05', 0, 'Member', NULL),
('ViperPlays', '64ed25e20316b5f99d2abddb7fc1f9968067009908ba4eb5e258a2b90198e460', '2020-10-13', 0, 'Member', NULL),
('VortexGamer', '1512bfab62e9589e4e390545d9b6bc02f3d072ca493a38a98358cbf5f489ccfd', '2021-12-01', 0, 'Member', NULL),
('WarriorElite', 'ca791a3198fed73341cbaa78823236b3d15f71309a2f81dd776f2f5b01ad9f27', '2020-08-05', 0, 'Member', NULL),
('WarriorX', 'b1d03f9995d4dbb89fc8461f9f3cc055920d100cfa989f345bfb4e1210105be0', '2021-01-19', 0, 'Member', NULL),
('WolfXPlayer', '4124fa6ec425efd1b3ffe38d1d4c3f0f4b303f5d9c24b4f83896ac86fece2a98', '2020-01-09', 0, 'Member', NULL),
('XenonPlayer', 'e7d2cec0917f676e07ccc848551152c5177fb3269160fadb5f10125b700ccb88', '2022-04-06', 0, 'Member', NULL),
('ZenoGamer', '07a0654ec03322aa931742f1cec5c968404c855ffd4a5d3a3276169ff8d53631', '2022-11-30', 0, 'Member', NULL);

-- --------------------------------------------------------

--
-- Struttura della tabella `votes_comments`
--

CREATE TABLE `votes_comments` (
  `username` varchar(24) NOT NULL,
  `comment_id` bigint(20) NOT NULL,
  `thread_id` bigint(20) NOT NULL,
  `vote` tinyint(4) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dump dei dati per la tabella `votes_comments`
--

INSERT INTO `votes_comments` (`username`, `comment_id`, `thread_id`, `vote`) VALUES
('LightHealer', 44, 51, 1);

-- --------------------------------------------------------

--
-- Struttura della tabella `votes_threads`
--

CREATE TABLE `votes_threads` (
  `username` varchar(24) NOT NULL,
  `thread_id` bigint(20) NOT NULL,
  `vote` tinyint(4) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dump dei dati per la tabella `votes_threads`
--

INSERT INTO `votes_threads` (`username`, `thread_id`, `vote`) VALUES
('AlessioSica', 55, 1),
('AlessioSica', 58, 1),
('DarkSlayer', 55, 1),
('LightHealer', 51, 1),
('LightHealer', 55, 1),
('LightHealer', 58, 1),
('Moderator1000!', 55, 1);

--
-- Indici per le tabelle scaricate
--

--
-- Indici per le tabelle `categories`
--
ALTER TABLE `categories`
  ADD PRIMARY KEY (`name`);

--
-- Indici per le tabelle `comments`
--
ALTER TABLE `comments`
  ADD PRIMARY KEY (`id`),
  ADD KEY `thread_id` (`thread_id`),
  ADD KEY `username` (`username`);

--
-- Indici per le tabelle `roles`
--
ALTER TABLE `roles`
  ADD PRIMARY KEY (`name`);

--
-- Indici per le tabelle `threads`
--
ALTER TABLE `threads`
  ADD PRIMARY KEY (`id`),
  ADD KEY `category` (`category`),
  ADD KEY `username` (`username`);

--
-- Indici per le tabelle `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`username`),
  ADD UNIQUE KEY `auth_token` (`auth_token`),
  ADD KEY `role` (`role`);

--
-- Indici per le tabelle `votes_comments`
--
ALTER TABLE `votes_comments`
  ADD PRIMARY KEY (`username`,`comment_id`),
  ADD KEY `comment_id` (`comment_id`);

--
-- Indici per le tabelle `votes_threads`
--
ALTER TABLE `votes_threads`
  ADD PRIMARY KEY (`username`,`thread_id`),
  ADD KEY `thread_id` (`thread_id`);

--
-- AUTO_INCREMENT per le tabelle scaricate
--

--
-- AUTO_INCREMENT per la tabella `comments`
--
ALTER TABLE `comments`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=70;

--
-- AUTO_INCREMENT per la tabella `threads`
--
ALTER TABLE `threads`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=81;

--
-- Limiti per le tabelle scaricate
--

--
-- Limiti per la tabella `comments`
--
ALTER TABLE `comments`
  ADD CONSTRAINT `comments_ibfk_1` FOREIGN KEY (`username`) REFERENCES `users` (`username`) ON DELETE SET NULL ON UPDATE CASCADE,
  ADD CONSTRAINT `comments_ibfk_2` FOREIGN KEY (`thread_id`) REFERENCES `threads` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Limiti per la tabella `threads`
--
ALTER TABLE `threads`
  ADD CONSTRAINT `threads_ibfk_2` FOREIGN KEY (`category`) REFERENCES `categories` (`name`) ON DELETE SET NULL ON UPDATE CASCADE,
  ADD CONSTRAINT `threads_ibfk_3` FOREIGN KEY (`username`) REFERENCES `users` (`username`) ON DELETE SET NULL ON UPDATE CASCADE;

--
-- Limiti per la tabella `users`
--
ALTER TABLE `users`
  ADD CONSTRAINT `users_ibfk_1` FOREIGN KEY (`role`) REFERENCES `roles` (`name`) ON UPDATE CASCADE;

--
-- Limiti per la tabella `votes_comments`
--
ALTER TABLE `votes_comments`
  ADD CONSTRAINT `votes_comments_ibfk_1` FOREIGN KEY (`username`) REFERENCES `users` (`username`) ON DELETE CASCADE,
  ADD CONSTRAINT `votes_comments_ibfk_2` FOREIGN KEY (`comment_id`) REFERENCES `comments` (`id`) ON DELETE CASCADE;

--
-- Limiti per la tabella `votes_threads`
--
ALTER TABLE `votes_threads`
  ADD CONSTRAINT `votes_threads_ibfk_1` FOREIGN KEY (`username`) REFERENCES `users` (`username`) ON DELETE CASCADE,
  ADD CONSTRAINT `votes_threads_ibfk_2` FOREIGN KEY (`thread_id`) REFERENCES `threads` (`id`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
