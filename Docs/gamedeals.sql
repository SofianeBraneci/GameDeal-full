SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";

--
-- Base de données : `gamedeals`
--

-- --------------------------------------------------------

--
-- Structure de la table `Comments`
--

CREATE TABLE `Comments` (
  `commentID` int(11) NOT NULL,
  `userId` int(11) NOT NULL,
  `comment` text NOT NULL,
  `gameId` int(11) NOT NULL,
  `date` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `Comments`
--

INSERT INTO `Comments` (`commentID`, `userId`, `comment`, `gameId`, `date`) VALUES
(29, 13, 'I love this game, can\'t wait to play Watch Dogs: Legion', 153679, 'dim., 23 mai 2021 20:14:59'),
(30, 13, 'Hope 2k22 will integrate more game options', 217775, 'dim., 23 mai 2021 20:17:34'),
(31, 15, 'Oh ! Tekken, what childhood memories !', 192149, 'dim., 23 mai 2021 20:18:30'),
(32, 15, 'In such a hurry to have the 6 to see Giancarlo Esposito in action !!!!', 169242, 'dim., 23 mai 2021 20:19:30'),
(33, 14, 'Dragon Ball fighter Z for this price ????? Let\'s GOOOOOOO', 174473, 'dim., 23 mai 2021 20:20:25'),
(34, 14, 'I tried Legion, I prefer the first Watch Dogs, it was a delight !', 153679, 'dim., 23 mai 2021 20:22:3');

-- --------------------------------------------------------

--
-- Structure de la table `Users`
--

CREATE TABLE `Users` (
  `id` int(11) NOT NULL,
  `firstname` varchar(255) NOT NULL,
  `lastname` varchar(255) NOT NULL,
  `login` varchar(255) NOT NULL,
  `password` blob NOT NULL,
  `email` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `Users`
--

INSERT INTO `Users` (`id`, `firstname`, `lastname`, `login`, `password`, `email`) VALUES
(12, 'Test', 'Gamedeals', 'adminGD', 0x61646d696e674744, 'test@gamedeals.fr'),
(13, 'Walid', 'Sadat', 'walidsadat', 0x77616c69647361646174, 'walid.sadat@etu.upmc.fr'),
(14, 'Sofiane', 'Braneci', 'sofianebraneci', 0x736f6669616e656272616e656369, 'braneci.sofiane@etu.upmc.fr'),
(15, 'Koceila', 'Azzoug', 'koceilaazzoug', 0x6b6f6365696c61617a7a6f7567, 'koceila.azzoug@etu.upmc.fr');

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `Comments`
--
ALTER TABLE `Comments`
  ADD PRIMARY KEY (`commentID`),
  ADD KEY `userId` (`userId`);

--
-- Index pour la table `Users`
--
ALTER TABLE `Users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `login` (`login`),
  ADD UNIQUE KEY `email` (`email`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `Comments`
--
ALTER TABLE `Comments`
  MODIFY `commentID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=35;

--
-- AUTO_INCREMENT pour la table `Users`
--
ALTER TABLE `Users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `Comments`
--
ALTER TABLE `Comments`
  ADD CONSTRAINT `Comments_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `Users` (`id`) ON DELETE CASCADE;
