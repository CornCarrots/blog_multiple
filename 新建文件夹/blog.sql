-- MySQL dump 10.14  Distrib 5.5.60-MariaDB, for Linux (x86_64)
--
-- Host: localhost    Database: myblog
-- ------------------------------------------------------
-- Server version	5.5.60-MariaDB

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `article`
--

DROP TABLE IF EXISTS `article`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `article` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) DEFAULT NULL,
  `summary` varchar(255) DEFAULT NULL,
  `text` mediumtext,
  `secret` varchar(255) DEFAULT NULL,
  `cid` int(11) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `updateDate` datetime DEFAULT NULL,
  `status_` int(2) DEFAULT '0',
  `type` varchar(255) DEFAULT NULL,
  `view` bigint(20) DEFAULT '0',
  `like_` bigint(20) DEFAULT '0',
  `start` bigint(20) DEFAULT '0',
  `comment` bigint(20) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `article`
--

LOCK TABLES `article` WRITE;
/*!40000 ALTER TABLE `article` DISABLE KEYS */;
INSERT INTO `article` VALUES (2,'Listary','一款真正提高效率的电脑神器！','<p>\n	<span style=\"font-size:18px;\"><strong>前言：</strong></span> \n</p>\n<p>\n	&nbsp; &nbsp;Listary是一款用于Windows的文件名定位/搜索辅助软件。它为Windows传统低效的文件打开/保存对话框提供了便捷、人性化的文件（夹）定位方式，同时改善了常见文件管理器中文件夹切换的效率。\n</p>\n<p>\n	<span style=\"font-size:18px;\"><strong>主要功能：</strong></span> \n</p>\n<p>\n	&nbsp; &nbsp;<strong>一：全盘搜索（随时随地双击Ctrl）</strong> \n</p>\n<p>\n	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img src=\"/blog/image/article/2019/02/14/20192141550135717148.jpg\" alt=\"\" /> \n</p>\n<p>\n	&nbsp; &nbsp;对于一些中文名称的搜索，可以直接输入中文的首字母搜索，如搜索鲁大师阿可以写为：lds，这样启动软件也可以快速启动啦。而对于一些英文名称的文件或文件夹，则直接输入英文字母搜索。\n</p>\n<p>\n	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img src=\"/blog/image/article/2019/02/14/20192141550135769273.jpg\" alt=\"\" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\n</p>\n<p>\n	&nbsp; &nbsp;<span style=\"font-size:16px;\"><strong> 二：快速定位（直接在文件夹输入名称）</strong></span> \n</p>\n<p>\n	<strong>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</strong>当我临时需要查找一个文件时，只需要在文件夹中输入名称即可。&nbsp;\n</p>\n<p>\n	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img src=\"/blog/image/article/2019/02/14/20192141550135957767.jpg\" alt=\"\" width=\"600\" height=\"375\" title=\"\" align=\"\" /> \n</p>\n<p>\n	&nbsp; &nbsp; 同时，你也可以通过右键点击文件进行各种操作：\n</p>\n<p>\n	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img src=\"/blog/image/article/2019/02/14/20192141550136275861.jpg\" alt=\"\" /> \n</p>\n<p>\n	&nbsp; &nbsp; <strong><span style=\"font-size:16px;\">其他有趣的功能，等你们自己去发现：</span></strong> \n</p>\n<p>\n	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img src=\"/blog/image/article/2019/02/14/20192141550136393370.jpg\" alt=\"\" width=\"550\" height=\"491\" title=\"\" align=\"\" /> \n</p>\n<p>\n	&nbsp; &nbsp; 喜欢这款软件吗？喜欢就快快留言吧！\n</p>','软件下载地址：直接到官网即可下载（https://www.listary.com）',5,'2019-02-19 07:55:57','2019-02-19 07:55:57',0,'TYPE_PUBLISH',3,0,0,5),(3,'Ditto','让你爱上“ctrl+c”的神器，从此不再为写文档发愁啦','<p>\n	<span style=\"font-size:18px;\"><strong>引言</strong><strong>：</strong></span> \n</p>\n<p>\n	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Ditto 是 Windows 下一款免费开源的剪贴板增强软件，使用它，你只需像往常一些复制东西，然后按下Ctrl+`（数字1前面那个按键），就可以唤出它的界面了，里面记录了所有的复制内容，你可以选择性进行粘贴，非常的方便。\n</p>\n<p>\n	<strong><span style=\"font-size:18px;\">使用方法：</span></strong> \n</p>\n<p>\n	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;一：可以随意地多次复制文本，图片等文件。\n</p>\n<p>\n	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp; 二：通过快捷键唤出界面，选择性黏贴。\n</p>\n<p>\n	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img src=\"/blog/image/article/2019/02/15/20192151550245817153.jpg\" alt=\"\" /> \n</p>\n<p>\n	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp; 可以通过选项可以设置快捷键，数据存储位置\n</p>\n<p>\n	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img src=\"/blog/image/article/2019/02/15/20192151550245874946.jpg\" alt=\"\" width=\"500\" height=\"521\" title=\"\" align=\"\" /> \n</p>\n<p>\n	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp; 三：可以对已复制内容进行精确搜索\n</p>\n<p>\n	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img src=\"/blog/image/article/2019/02/15/20192151550245947800.jpg\" alt=\"\" />\n</p>','链接：https://pan.baidu.com/s/1zLhykqnpKQRjmIClyvc1Og  提取码：sfs9  复制这段内容后打开百度网盘手机App，操作更方便哦',5,'2019-02-19 07:56:24','2019-02-19 07:56:24',0,'TYPE_PUBLISH',2,0,0,1),(4,'QuickLook','从此只需要点击空格键就可以知道你的文件内容了','<p>\n	<span style=\"font-size:18px;\"><strong>引言：</strong></span>\n</p>\n<p>\n	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;「QuickLook」是一款开源、免费的文件快速预览辅助工具，支持图片、音视频、文档、代码、压缩包等内容，可让用户在使用 Windows 系统时也能获得与 Mac 同样高效快速便捷的文件预览体验。\n</p>\n<p>\n	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;其实，我们常常在整理、查找文件时，大多时候只想快速查看一下文件内容，而并非想去编辑它。比如Office 文档、.PSD 文件等。特别是像站长写程序时，有时候总是会忘记了一些文本文件有哪些内容，有了“空格预览文件功能”后，我们在选中文件后，只需一键即可迅速弹出预览窗口。也减少了打开大文件时的等待时间，大大提高了工作效率。\n</p>\n<p>\n	<span style=\"font-size:18px;\"><strong>使用方法：</strong></span>\n</p>\n<p>\n	<span style=\"font-size:18px;\"><strong>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;</strong></span> 首先：先安装QuickLook程序，此时QuickLook支持以下格式：\n</p>\n<p>\n	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style=\"background-color:#FFFFFF;color:#337FE5;\">&nbsp;&nbsp;&nbsp; </span><span style=\"background-color:#FFFFFF;color:#337FE5;\"><span style=\"font-family:Arial Black;\">&nbsp; &nbsp;&nbsp;</span><strong><span style=\"font-family:Arial Black;\">图片：png、jpg、bmp、gif</span></strong></span>\n</p>\n<p>\n	<span style=\"background-color:#FFFFFF;color:#337FE5;font-family:&quot;\"><strong>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp; 压缩包：zip、rar、7z</strong></span>\n</p>\n<p>\n	<span style=\"background-color:#FFFFFF;color:#337FE5;font-family:&quot;\"><strong>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp; 项目文件：PDF、AI等</strong></span>\n</p>\n<p>\n	<span style=\"background-color:#FFFFFF;color:#337FE5;font-family:&quot;\"><strong>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp; 文本文件：txt、py、c、cpp、sin等</strong></span>\n</p>\n<p>\n	<span style=\"background-color:#FFFFFF;color:#337FE5;font-family:&quot;\"><strong>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp; 视频文件：mp4、mkv等</strong></span>\n</p>\n<p>\n	<span style=\"background-color:#FFFFFF;color:#337FE5;font-family:&quot;\"><strong>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp; 音乐文件：mp3、m4a等</strong></span>\n</p>\n<p>\n	<span style=\"background-color:#FFFFFF;color:#337FE5;font-family:&quot;\"><strong>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp; HTML文件：html、htm</strong></span>\n</p>\n<p>\n	<span style=\"background-color:#FFFFFF;color:#337FE5;font-family:&quot;\"><strong>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp; Mardown文件：md</strong></span>\n</p>\n<p>\n	<strong><span style=\"font-size:14px;\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span></strong>&nbsp; 其次：如果你需要预览office三件套文件，只需要前往github\n</p>\n<p>\n	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;“<a href=\"https://github.com/QL-Win/QuickLook/wiki/Available-Plugins\" target=\"_blank\">https://github.com/QL-Win/QuickLook/wiki/Available-&nbsp;&nbsp;&nbsp;&nbsp;Plugins</a><span id=\"__kindeditor_bookmark_start_233__\">”</span>\n</p>\n<p>\n	<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;下载插件即可，站长已经帮你们下载好了：</span>\n</p>\n<p>\n	<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img src=\"/blog/image/article/2019/02/16/20192161550283956371.jpg\" alt=\"\" width=\"328\" height=\"200\" title=\"\" align=\"\" /><br />\n</span>\n</p>\n<p>\n	<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp; 首先打开已经安装的quickLook，然后选择插件，按空格，点击“Click here to install this plugin\"，然后重启软件&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;即可。<br />\n</span>\n</p>\n<p>\n	<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img src=\"/blog/image/article/2019/02/16/20192161550284184382.jpg\" alt=\"\" /><br />\n</span>\n</p>\n<p>\n	<span style=\"font-size:18px;\"><strong>使用效果：</strong></span>\n</p>\n<p>\n	<strong>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp; 预览音乐：</strong>\n</p>\n<p>\n	<strong>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img src=\"/blog/image/article/2019/02/16/20192161550284481949.jpg\" alt=\"\" /></strong>\n</p>\n<p>\n	<strong>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp; 预览图片：<br />\n</strong>\n</p>\n<p>\n	<strong>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img src=\"/blog/image/article/2019/02/16/20192161550284511511.jpg\" alt=\"\" /><br />\n</strong>\n</p>\n<p>\n	<strong>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp; 其他内容就等你们自己去发掘啦<br />\n</strong>\n</p>','链接：https://pan.baidu.com/s/1dUe7X4tLGtqsdrDpqC1Atw  提取码：ph4k  复制这段内容后打开百度网盘手机App，操作更方便哦',5,'2019-02-16 02:37:10','2019-02-16 02:37:10',0,'TYPE_PUBLISH',4,1,1,2),(5,'酷我音乐爆破版','站长个人非常喜欢的手机听歌神器','<p>\n	<span style=\"font-size:18px;\"><strong>前言：</strong></span> \n</p>\n<p>\n	&nbsp; &nbsp; &nbsp; &nbsp;手机APP酷我音乐破解豪华VIP版，破解SVIP豪华会员，任意账户登录可畅享以下特权，无需内购免费试听下载版权收费歌曲/专辑/无损音乐，任意账户登录即可免费使用极速下载特权，畅享加速专属服务器，VIP尊贵标识、下载高画质MV、过滤乐库广告、使用VIP皮肤等。\n</p>\n<p>\n	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img src=\"/blog/image/article/2019/02/18/20192181550484941741.jpg\" alt=\"\" width=\"250\" height=\"445\" title=\"\" align=\"\" />&nbsp; &nbsp; &nbsp;<img src=\"/blog/image/article/2019/02/18/20192181550485098577.jpg\" alt=\"\" width=\"250\" height=\"444\" title=\"\" align=\"\" /> \n</p>\n<p>\n	<span style=\"font-size:18px;\"><strong>特点：</strong></span> \n</p>\n<p>\n	&nbsp; &nbsp; &nbsp;<strong> &nbsp;1：智能煲机</strong> \n</p>\n<p>\n	&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;什么是煲机？对于很多人来说这也许是个比较陌生的词汇。所谓煲机，就是一种快速使器材老化稳定的措施，对于耳机来说，就是经过一段时间有选择的音乐播放，来达到最佳播放效果的方法。因为新耳机的配件也是新的，功能不稳定，会导致效果失真，所以需要\"煲机\"使耳机进入使用黄金期。说白了，煲机就是一种磨合，就像开新车、穿新鞋一样，都是经过一段时间才能达到最优的使用效果。\n</p>\n<p>\n	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img src=\"/blog/image/article/2019/02/18/20192181550485006830.jpg\" alt=\"\" width=\"259\" height=\"460\" title=\"\" align=\"\" />&nbsp; &nbsp; &nbsp;<img src=\"/blog/image/article/2019/02/18/20192181550485044999.jpg\" alt=\"\" width=\"250\" height=\"444\" title=\"\" align=\"\" /> \n</p>\n<p>\n	&nbsp; &nbsp; &nbsp; <strong>&nbsp;2：蝰蛇音效</strong> \n</p>\n<p>\n	&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;在播放音乐的过程中，我们常常会选择开启蝰蛇音效，从而获得细节与高音的补偿，酷我音乐开启蝰蛇音效后，能够支持3D美音、虚拟现场、超重低音、纯净人声等四种不同的效果，满足您的不同需求！\n</p>\n<p>\n	&nbsp; &nbsp; &nbsp; <strong>&nbsp;3：fu费歌曲下载！</strong> \n</p>\n<p>\n	&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;这个功能无需多说，老司机都懂。\n</p>\n<p>\n	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img src=\"/blog/image/article/2019/02/18/20192181550485142567.jpg\" alt=\"\" width=\"169\" height=\"300\" title=\"\" align=\"\" />&nbsp; &nbsp; &nbsp; &nbsp;&nbsp;\n</p>','链接：https://pan.baidu.com/s/1JKNfRDriLj3jCsQtDz5_-A  提取码：a5d9  复制这段内容后打开百度网盘手机App，操作更方便哦',6,'2019-02-18 10:32:56','2019-02-18 10:32:56',0,'TYPE_PUBLISH',0,0,0,0),(7,'新方圆影视+视频源','听说你想看流浪地球？','<p>\n	<span style=\"font-size:18px;\"><strong>前言：</strong></span>\n</p>\n<p>\n	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;市场上很多视频APP，大多是固定的视频网站获取信息，那么能不能像小说一样通过规则解析视频呢，能够自定义规则，获取视频，通用匹配所有视频网站，这正是方圆影视希望做到的\n</p>\n<p>\n	<span style=\"font-size:18px;\"><strong>特点：</strong></span>\n</p>\n<p>\n	<p>\n		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style=\"color:#E53333;\"><strong>1</strong></span>、简洁的界面，简单的操作，丰富的资源还没有广告；\n	</p>\n	<p>\n		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img src=\"/blog/image/article/2019/02/18/20192181550487806041.jpg\" alt=\"\" width=\"250\" height=\"444\" title=\"\" align=\"\" />&nbsp; &nbsp; &nbsp;<img src=\"/blog/image/article/2019/02/18/20192181550487828354.jpg\" alt=\"\" width=\"250\" height=\"444\" title=\"\" align=\"\" />\n	</p>\n	<p>\n		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style=\"color:#337FE5;\"><strong>2</strong></span>、分类详细，地区、时间以及类别，还有强大的搜索引擎；\n	</p>\n	<p>\n		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img src=\"/blog/image/article/2019/02/18/20192181550487853772.jpg\" alt=\"\" width=\"250\" height=\"444\" title=\"\" align=\"\" />&nbsp; &nbsp; &nbsp;<img src=\"/blog/image/article/2019/02/18/20192181550487873551.jpg\" alt=\"\" width=\"250\" height=\"444\" title=\"\" align=\"\" />\n	</p>\n	<p>\n		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style=\"color:#FF9900;\">&nbsp;</span><span style=\"color:#FF9900;\"><strong>3</strong></span>、通过解析视频源网站获得视频信息，资源多且丰富，部分支持下载；\n	</p>\n	<p>\n		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img src=\"/blog/image/article/2019/02/18/20192181550487901324.jpg\" alt=\"\" width=\"250\" height=\"444\" title=\"\" align=\"\" />&nbsp; &nbsp; &nbsp;<img src=\"/blog/image/article/2019/02/18/20192181550487918069.jpg\" alt=\"\" width=\"250\" height=\"444\" title=\"\" align=\"\" />\n	</p>\n	<p>\n		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp; <span style=\"color:#009900;\"><strong>4</strong></span>、投屏，截图，下载，x5内核在线播放等功能一应俱全。\n	</p>\n</p>','链接：https://pan.baidu.com/s/1iuEriBi8-JJVEUwA3gcvNA  提取码：ddds  复制这段内容后打开百度网盘手机App，操作更方便哦',6,'2019-02-18 11:08:15','2019-02-18 11:08:15',0,'TYPE_PUBLISH',0,0,0,0),(8,'网站，急用，在线等','临时的网站解决你临时的需求','<p>\n	<span style=\"font-size:18px;color:#337FE5;\"><strong>一：临时邮箱（<a href=\"http://24mail.chacuo.net/\" target=\"_blank\">http://24mail.chacuo.net/</a>）</strong></span>\n</p>\n<p>\n	<span style=\"font-size:18px;\"></span>\n</p>\n<p>\n	<span style=\"font-size:14px;\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;很多时候我们访问某些论坛或者网站，需要注册一个用户才能使用完整功能，而现在用户注册除了手机外，大多数还是需要一个电子邮箱才行。</span>\n</p>\n<p>\n	<span style=\"font-size:14px;\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;而我们注册这些论坛，可能注册完使用了网站的功能之后，以后就不会再用到了。如果使用我们的个人邮箱，免不了收到很多广告或者推销等垃圾邮件。如果使用临时邮箱注册，注册完之后这些临时邮箱就不再需要了。</span>\n</p>\n<p>\n	<span style=\"font-size:14px;\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img src=\"/blog/image/article/2019/02/19/20192191550562140084.jpg\" alt=\"\" width=\"650\" height=\"333\" title=\"\" align=\"\" /><br />\n</span>\n</p>\n<p>\n	<span style=\"font-size:18px;color:#E56600;\"><strong>二：临时软件（<a href=\"https://uzer.me/\" target=\"_blank\">https://uzer.me/</a>）</strong></span>\n</p>\n<p>\n	<span style=\"font-size:14px;\"></span>\n</p>\n<p>\n	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;user.me，一个可以免插件在线使用任何软件的网站，网站上的任何软件无需下载安装，即点即用，真正的实现免插件云端打开、编辑、保存任何文件。并且，EaaS平台内的所有软件都支持多人协作编辑，用户只需要简单的分享一个链接，无论对方使用什么设备，或者有没有安装相应格式的软件，都能轻而易举的实现多人应用协作。\n</p>\n<p>\n	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img src=\"/blog/image/article/2019/02/19/20192191550562444515.jpg\" alt=\"\" width=\"650\" height=\"330\" title=\"\" align=\"\" />\n</p>\n<p>\n	<span style=\"font-size:18px;color:#009900;\"><strong>三：拷贝兔（<a href=\"https://cp.anyknew.com/\" target=\"_blank\">https://cp.anyknew.com/</a>）</strong></span>\n</p>\n<p>\n	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;拷贝兔(可以实现不同设备间文件或文本的临时性快速分享的实用工具。比如你打印时需要把文件从一台电脑传到另外一台电脑、临时需要把Mac上的一个APK文件发送到Android设备、把一些软件配置参数从Windows传到iPad…但又不想安装各种同步软件进行各种设置各种登录，通过二维码或提取码你可以在几秒中内可以在Windows、Mac、Linux、iOS、Android等各种有浏览器的设备间完成原先耗时且繁琐的分享操作。\n</p>\n<p>\n	<br />\n</p>\n&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img src=\"/blog/image/article/2019/02/19/20192191550562610220.jpg\" alt=\"\" width=\"650\" height=\"329\" title=\"\" align=\"\" /><br />\n<p>\n	<br />\n</p>\n<p>\n	<span style=\"font-size:18px;color:#E53333;\"><strong>四：奶牛快传（<a href=\"https://cowtransfer.com/\" target=\"_blank\">https://cowtransfer.com/</a>）</strong></span>\n</p>\n<p>\n	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;奶牛快传 是一款无需下载app，无需注册即可使用的大文件临时传输服务，未注册用户支持最大 2GB 文件，72小时保存时效，登录后无限文件大小，168小时保存时效。国内线路，速度飞快，不限速！！！\n</p>\n<p>\n	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;<img src=\"/blog/image/article/2019/02/19/20192191550562829133.jpg\" alt=\"\" width=\"650\" height=\"330\" title=\"\" align=\"\" />\n</p>','(*^__^*) 嘻嘻……',10,'2019-02-19 07:55:11','2019-02-19 07:55:11',0,'TYPE_PUBLISH',0,0,0,0),(9,'VIP电影','站长，我...我想看VIP电影','<p>\n	<span style=\"font-size:18px;\"><strong>前言：</strong></span>&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;\n</p>\n<p>\n	<br />\n</p>\n<p>\n	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp; 大家平时都是去某酷、某讯、某奇艺看电影吧，可是你们会发现这些网站要么没有新片，要么收费。那么去网上下载高清的呢？各种弹窗广告满屏飞，稍微不注意就中招。\n</p>\n<p>\n	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp; 今天，站长就给大家分享几款可以在线看vip电影的网站吧\n</p>\n<p>\n	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <strong><span style=\"color:#337FE5;\">&nbsp; 一：电影蜜蜂 （<a href=\"https://www.dybee.tv/\" target=\"_blank\">https://www.dybee.tv/</a>）</span></strong> \n</p>\n<p>\n	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;电影蜜蜂为喜欢电影的用户提供2019最新热门电影，欧美、国产、港台、日韩等等电影一应俱全，如果你找不到自己想看的电影，也可以留言求片。网站还提供了在线观看和网盘下载等功能。\n</p>\n<p>\n	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img src=\"/blog/image/article/2019/02/19/20192191550565341055.jpg\" alt=\"\" width=\"650\" height=\"330\" title=\"\" align=\"\" />\n</p>\n<p>\n	<span style=\"color:#E53333;\"><strong>&nbsp;&nbsp;&nbsp;&nbsp;</strong></span><span style=\"color:#E53333;\"><strong>&nbsp; &nbsp; 二：Neets追剧专家（<a href=\"https://neets.cc/\" target=\"_blank\">https://neets.cc/</a>）</strong></span>\n</p>\n<p>\n	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;<span style=\"background-color:#FFFFFF;color:#333333;font-family:arial, 宋体, sans-serif;\">Neets的产品核心是订阅。订阅之后，人和条目建立联系，生成列表，成为私人播单。可以很快时间收到Neets发出的剧集更新提醒，还能随时标记观看进度。同时，Neets会对用户的搜索、订阅、观看行为等数据进行智能分析。</span><span style=\"background-color:#FFFFFF;color:#333333;font-family:arial, 宋体, sans-serif;\">操作简单便捷，通过简单的搜索，用户能轻松地获得数据资源。不仅有详尽的条目信息，比如对剧集的时间、国家地区、是否连载以及新热等做出了详细的分类，并打上了“分数”，还提供众多可选的资源。</span><span style=\"background-color:#FFFFFF;color:#333333;font-family:arial, 宋体, sans-serif;\"><br />\n</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;<img src=\"/blog/image/article/2019/02/19/20192191550565599702.jpg\" alt=\"\" width=\"650\" height=\"331\" title=\"\" align=\"\" />\n</p>\n<p>\n	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;<strong><span style=\"color:#009900;\"> 三：思古影视（<a href=\"http://v.sigu.me/index.php\" target=\"_blank\">http://v.sigu.me/index.php</a>）</span></strong>\n</p>\n<p>\n	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp; 网站提供了电影，电视剧，综艺，电视直播等资源&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp;\n</p>\n<p>\n	<br />\n</p>','(*^__^*) 嘻嘻……',10,'2019-02-19 08:44:35','2019-02-19 08:44:35',0,'TYPE_PUBLISH',0,0,0,0),(10,'番茄土豆','这可不是一道菜哦','<p>\n	<span style=\"font-size:18px;\"><strong>前言：</strong></span>\n</p>\n<p>\n	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;番茄土豆商务软件，运用了番茄工作法，它可以帮助你计划、管理、执行、记录工作，大大提高你的工作效率。现在就开始使用番茄土豆，完成了不起的工作！它简单、直观的对工作进行计划、管理、执行和记录，支持多媒体间的同步和记录，是你工作的好帮手。\n</p>\n<p>\n	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img src=\"/blog/image/article/2019/02/20/20192201550632736381.jpg\" alt=\"\" />\n</p>\n<p>\n	<span style=\"font-size:18px;\"><strong>特点：</strong></span>\n</p>\n<ul>\n	<li>\n		<strong><span style=\"color:#337FE5;\">开始一个番茄</span></strong>\n	</li>\n</ul>\n<p>\n	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;认真投入工作25分钟，将在屏幕上倒计时，并在最后提醒你记录下这25分钟内你完成的工作(tips：1，可以开启浏览器通知哦;2，使用“#标签”可以为完成的番茄添加标签);\n</p>\n<ul>\n	<li>\n		<strong><span style=\"color:#E53333;\">休息&nbsp; &nbsp;&nbsp;</span></strong><strong><span style=\"color:#E53333;\">&nbsp;</span></strong>&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;\n	</li>\n</ul>\n<p>\n	<p>\n		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;每个25分钟后然都需要休息5分钟，番茄土豆也在倒计时，不要再看电脑啦，抓紧时间好好放松看看风景吧！\n	</p>\n	<p>\n		<ul>\n			<li>\n				<strong><span style=\"color:#009900;\"></span></strong><strong><span style=\"color:#009900;\">开始另一个番茄</span></strong>\n			</li>\n		</ul>\n	</p>\n&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;5分钟后，开始一个新的番茄，新的25分钟。\n</p>\n<p>\n	<ul>\n		<li>\n			<strong><span style=\"color:#FF9900;\">新想法，新任务</span></strong><strong><span style=\"color:#FF9900;\"></span></strong>\n		</li>\n	</ul>\n</p>\n<p>\n	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;如果你有了新的想法或者新的任务，及时把它添加到土豆列表中(tips：在土豆结尾添加半角的感叹号可以标记重要程度)，安排时间将它完成(tips：番茄钟计时结束时，勾选完成，土豆内容将自动添加到番茄记录)\n</p>\n<p>\n	<ul>\n		<li>\n			<strong><span style=\"color:#00D5FF;\">统计报表</span></strong><strong><span style=\"color:#00D5FF;\"></span></strong>\n		</li>\n	</ul>\n</p>\n<p>\n	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;完成一段时间的工作后，你可以通过“查看统计”看到自己的每日完成的番茄情况，以及各种标签的分布。顺便完成了时间记录的工作。\n</p>\n<p>\n	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img src=\"/blog/image/article/2019/02/20/20192201550632757369.jpg\" alt=\"\" />\n</p>','直接前往官网：https://www.pomotodo.com/ 下载即可',5,'2019-02-20 03:20:00','2019-02-20 03:20:00',0,'TYPE_PUBLISH',0,0,0,0),(11,'LightBulb','一款值得称赞的人性化电脑端护眼软件','<p>\n	<strong><span style=\"font-size:18px;\">前言：</span></strong> \n</p>\n<p>\n	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;它可以根据每天的日出日落信息，结合周围的光线强度来调整屏幕色温，可以对您的眼睛产生极好的保护效果，为了工作长期对着电脑的朋友们，快来使用吧！\n</p>\n<p>\n	<span style=\"font-size:18px;\"><strong>特点：</strong></span> \n</p>\n<p>\n	&nbsp; &nbsp; &nbsp; &nbsp; LightBulb 是一个屏幕色温调节软件和蓝光过滤器，可以减少蓝光输出，达到护眼效果，它与著名的 f.lux 是同类型软件，不过 LightBulb 是一个开源软件。\n</p>\n<p>\n	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;LightBulb 能够根据日出日落时间，自动调整屏幕颜色，特别是在晚上时间让屏幕看起来颜色更暖些，眼睛就不会那么疲劳，适合长时间盯着屏幕看的情况。\n</p>\n<p>\n	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp;<img src=\"/blog/image/article/2019/02/20/20192201550635901041.jpg\" alt=\"\" width=\"260\" height=\"260\" title=\"\" align=\"\" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img src=\"/blog/image/article/2019/02/20/20192201550635923185.jpg\" alt=\"\" width=\"260\" height=\"260\" title=\"\" align=\"\" /> \n</p>\n<p>\n	<br />\n</p>','链接：https://pan.baidu.com/s/1KPF6ThmkdwA9cEC7vEo2PA  提取码：9wxa  复制这段内容后打开百度网盘手机App，操作更方便哦',5,'2019-02-20 04:23:38','2019-02-20 04:23:38',0,'TYPE_PUBLISH',0,0,0,0),(12,'IDM爆破版','快！太快了！我就需要这么快的下载神器！','<p>\n	<span style=\"font-size:18px;\"><strong>前言：</strong></span>\n</p>\n<p>\n	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Internet Download Manager (简称IDM) &nbsp;是 Windows 平台老牌而功能强大的下载工具，软件提供了下载队列、站点抓取和映射服务器等功能的同时，支持多款浏览器，对于经常有下载需求的用户来说，是个不可多得的选择。它是国外热门的多线程下载工具，支持多媒体下载、自动捕获链接、自动识别文件名、静默下载、批量下载、计划下载任务、站点抓取、队列与网盘支持等。\n</p>\n<p>\n	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img src=\"/blog/image/article/2019/02/21/20192211550717024814.jpg\" alt=\"\" width=\"650\" height=\"315\" title=\"\" align=\"\" />\n</p>\n<p>\n	<br />\n</p>\n<p>\n	<span style=\"font-size:18px;\"><strong>使用方法：</strong></span>\n</p>\n<p>\n	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp; 一：打开安装版这个文件，解压并安装。\n</p>\n<p>\n	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp; 二：打开安装版注册机，点击Patch，选择IDMan这个文件，然后FirstName和LastName都输入1，即可破解成功。\n</p>\n<p>\n	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp; 三：安装插件，在IDM安装文件中找到crx为后缀的文件，直接将文件拖进谷歌浏览器即可。\n</p>\n<p>\n	<span style=\"font-size:18px;\"><strong>软件特点：</strong></span>\n</p>\n<p>\n	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp; 一：IDM可以直接下载网页中的视频。\n</p>\n<p>\n	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp; 二：IDM无法安装种子文件，但是可以将种子文件先保存在百度网盘中，再下载。\n</p>\n<p>\n	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp; 三：IDM配合油候插件可以实现百度云高速下载，这个我会在以后的文章中详细说明哈哈，敬请期待~\\(≧▽≦)/~\n</p>','链接：https://pan.baidu.com/s/1ow8AC3QCgr4oaeK8h0qkBw  提取码：zn2a  复制这段内容后打开百度网盘手机App，操作更方便哦',5,'2019-02-21 02:51:10','2019-02-21 02:51:10',0,'TYPE_PUBLISH',0,0,0,0),(13,'迅雷也太快了吧','我可没有开通会员哦','<p>\n	<span style=\"font-size:18px;\"><strong>前言：</strong></span> \n</p>\n<p>\n	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;手机迅雷,是一款专业下载工具。全网手机资源智能推送、在线播放,高速获取手机电影、视频、音乐、游戏、电子书、软件等手机文件。不过站长今天给大家分享的是纯净！高速！破解版手雷！\n</p>\n<p>\n	用户体验：\n</p>\n<p>\n	&nbsp; &nbsp; &nbsp; &nbsp; 首先，站长打开了这个APP，发现启动页面十分简洁，站长打一百分！\n</p>\n<p>\n	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp; &nbsp; &nbsp;&nbsp;&nbsp;<img src=\"/blog/image/article/2019/02/21/20192211550720163938.jpg\" alt=\"\" width=\"260\" height=\"462\" title=\"\" align=\"\" />&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;<img src=\"/blog/image/article/2019/02/21/20192211550720186280.jpg\" alt=\"\" width=\"260\" height=\"462\" title=\"\" align=\"\" />\n</p>\n<p>\n	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp; 接着，站长在网上找了一个种子文件，试图下载敏感资源，发现它居然！！满速下载！！！\n</p>\n<p>\n	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img src=\"/blog/image/article/2019/02/21/20192211550720259489.jpg\" alt=\"\" width=\"260\" height=\"462\" title=\"\" align=\"\" />&nbsp; &nbsp; &nbsp; &nbsp;<img src=\"/blog/image/article/2019/02/21/20192211550720283685.jpg\" alt=\"\" width=\"260\" height=\"462\" title=\"\" align=\"\" />\n</p>\n<p>\n	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp; 而且，它还实现了边下边播！！站长忍不住了，文章就先码到这里吧......\n</p>\n<p>\n	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp;<img src=\"/blog/image/article/2019/02/21/20192211550720363827.jpg\" alt=\"\" width=\"260\" height=\"462\" title=\"\" align=\"\" />\n</p>','链接：https://pan.baidu.com/s/1IP-464r_gNXLRYwpz1ZD_g  提取码：bk85  复制这段内容后打开百度网盘手机App，操作更方便哦',6,'2019-02-21 03:25:58','2019-02-21 03:39:53',0,'TYPE_PUBLISH',0,0,0,0),(14,'MindManage','MindManager爆破版，是一个可视化的工具，可以用在脑力风暴和计划当中。提供给商务人士一个更有效的、电子化的手段来进行捕捉、组织和联系信息和想法。','<p>\n	<span style=\"font-size:18px;\"><strong>前言：</strong></span>\n</p>\n<p>\n	&nbsp; &nbsp; &nbsp; &nbsp; 这个就是鼎鼎大名的MindManager了，被誉为世界上最好用的思维导图工具,没有之一。Mindjet MindManager是一款优秀的思维导图和知识管理软件，能够帮助你将想法和灵感以清晰的思维导图的形式记录下来，这对梳理工作计划和规划很有帮助，广泛应用于研究、组织、解决问题和决策中。\n	<p>\n		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;使用MindManager能够快速、轻松创建优雅、漂亮的思维导图，有效完成信息的捕捉、分析和重新利用，另外MindManager也能与Microsoft Office集成，实现信息图表的导入导出、知识的创新和分享。\n	</p>\n	<p>\n		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img src=\"/blog/image/article/2019/02/22/20192221550802037785.jpg\" alt=\"\" width=\"335\" height=\"400\" title=\"\" align=\"\" />\n	</p>\n	<p>\n		<span style=\"font-size:18px;\"><strong>特点：</strong></span>\n	</p>\n	<p>\n		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp; 1、大量模板，满足你的一切需求！\n	</p>\n	<p>\n		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img src=\"/blog/image/article/2019/02/22/20192221550802134901.jpg\" alt=\"\" width=\"600\" height=\"305\" title=\"\" align=\"\" />\n	</p>\n	<p>\n		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp; 2、操作方便快捷，每个主题还可以添加链接图片等信息：\n	</p>\n	<p>\n		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img src=\"/blog/image/article/2019/02/22/20192221550802573416.jpg\" alt=\"\" width=\"600\" height=\"320\" title=\"\" align=\"\" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\n	</p>\n	<p>\n		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp; 3、支持导出为各种格式的文件。\n	</p>\n	<p>\n		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img src=\"/blog/image/article/2019/02/22/20192221550802702025.jpg\" alt=\"\" width=\"600\" height=\"544\" title=\"\" align=\"\" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\n	</p>\n	<p>\n		<strong>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;快来用它提高你的效率吧！</strong>\n	</p>\n</p>','链接：https://pan.baidu.com/s/1PsdcXNXEikGZyP0WDK7FAg  提取码：tec7  复制这段内容后打开百度网盘手机App，操作更方便哦',5,'2019-02-22 02:36:21','2019-02-22 02:36:21',0,'TYPE_PUBLISH',0,0,0,0);
/*!40000 ALTER TABLE `article` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `carousel`
--

DROP TABLE IF EXISTS `carousel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `carousel` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `status_` int(2) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `carousel`
--

LOCK TABLES `carousel` WRITE;
/*!40000 ALTER TABLE `carousel` DISABLE KEYS */;
INSERT INTO `carousel` VALUES (1,0),(2,0),(3,0);
/*!40000 ALTER TABLE `carousel` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `category` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `pid` int(11) NOT NULL DEFAULT '0',
  `icon` varchar(255) DEFAULT NULL,
  `status_` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES (1,'应用程序',0,'',0),(2,'教程资料',0,'',0),(3,'大神网站',0,'',0),(4,'个人博客',0,'',0),(5,'windows资源',1,'',0),(6,'安卓神器',1,'',0),(7,'TV应用',1,'',0),(8,'学习教程',2,'',0),(9,'精选素材',2,'',0),(10,'资源网站',3,'',0),(11,'素材网站',3,'',0),(12,'JAVA学习',4,'',0),(13,'计算机技术',4,'',0),(14,'其他',4,'',0);
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comments`
--

DROP TABLE IF EXISTS `comments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `comments` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `text` mediumtext,
  `createDate` datetime DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `pid` int(11) DEFAULT NULL,
  `uid` int(11) DEFAULT NULL,
  `aid` int(11) DEFAULT NULL,
  `like_` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comments`
--

LOCK TABLES `comments` WRITE;
/*!40000 ALTER TABLE `comments` DISABLE KEYS */;
INSERT INTO `comments` VALUES (3,'大家快来评论呀<img src=/blog/image/emoji/tieba/2.jpg>','2019-02-14 10:19:50',0,0,1,2,1),(4,'测试一下','2019-02-15 10:22:47',0,0,2,2,0),(5,'站长真帅','2019-02-16 02:39:01',0,0,2,4,1),(6,'站长好！','2019-02-16 02:39:54',0,0,2,3,0),(7,'站长好','2019-02-16 02:47:13',2,3,2,2,0),(8,'<img src=/blog/image/emoji/emoji/1.png>哈哈','2019-02-18 04:34:45',0,0,2,4,0);
/*!40000 ALTER TABLE `comments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `history`
--

DROP TABLE IF EXISTS `history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `history` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uid` int(11) NOT NULL,
  `aid` int(11) NOT NULL,
  `createDate` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `history`
--

LOCK TABLES `history` WRITE;
/*!40000 ALTER TABLE `history` DISABLE KEYS */;
INSERT INTO `history` VALUES (25,2,4,'2019-02-16 08:05:48'),(26,2,2,'2019-02-16 08:08:33'),(27,2,3,'2019-02-16 08:08:39'),(28,2,4,'2019-02-16 08:08:54'),(29,2,4,'2019-02-16 08:51:18'),(30,2,2,'2019-02-16 10:10:35'),(31,2,3,'2019-02-16 10:10:57'),(32,2,2,'2019-02-16 10:11:08'),(33,2,2,'2019-02-16 10:11:41'),(34,2,3,'2019-02-16 10:11:49'),(35,2,3,'2019-02-16 10:11:53'),(36,2,2,'2019-02-16 10:11:58'),(37,2,2,'2019-02-16 10:12:06'),(38,2,3,'2019-02-16 10:12:13'),(39,2,2,'2019-02-16 10:12:19'),(40,2,4,'2019-02-18 04:26:11'),(41,2,4,'2019-02-18 04:26:24'),(42,2,4,'2019-02-18 04:26:32'),(43,2,4,'2019-02-18 04:34:25'),(44,2,4,'2019-02-18 04:34:49');
/*!40000 ALTER TABLE `history` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `like_`
--

DROP TABLE IF EXISTS `like_`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `like_` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uid` int(11) NOT NULL,
  `acid` int(11) NOT NULL,
  `type` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `like_`
--

LOCK TABLES `like_` WRITE;
/*!40000 ALTER TABLE `like_` DISABLE KEYS */;
INSERT INTO `like_` VALUES (1,2,5,'type_comment'),(2,2,3,'type_comment'),(3,2,4,'type_article');
/*!40000 ALTER TABLE `like_` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `link`
--

DROP TABLE IF EXISTS `link`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `link` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `url` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `desc_` varchar(255) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `status_` int(2) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `link`
--

LOCK TABLES `link` WRITE;
/*!40000 ALTER TABLE `link` DISABLE KEYS */;
/*!40000 ALTER TABLE `link` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `log_`
--

DROP TABLE IF EXISTS `log_`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `log_` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `mid` int(11) DEFAULT NULL,
  `text` varchar(255) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `log_`
--

LOCK TABLES `log_` WRITE;
/*!40000 ALTER TABLE `log_` DISABLE KEYS */;
INSERT INTO `log_` VALUES (1,1,'登录后台管理系统','2019-02-13 08:32:14'),(2,1,'登录后台管理系统','2019-02-14 03:07:49'),(3,1,'登录后台管理系统','2019-02-14 03:49:45'),(4,1,'登录后台管理系统','2019-02-14 04:40:32'),(5,1,'登录后台管理系统','2019-02-14 04:41:29'),(6,1,'登录后台管理系统','2019-02-14 06:25:30'),(7,1,'登录后台管理系统','2019-02-14 06:25:30'),(8,1,'登录后台管理系统','2019-02-14 07:17:25'),(9,1,'登录后台管理系统','2019-02-14 07:55:42'),(10,1,'登录后台管理系统','2019-02-14 07:55:43'),(11,1,'登录后台管理系统','2019-02-14 08:22:07'),(12,1,'登录后台管理系统','2019-02-14 09:14:39'),(13,1,'登录后台管理系统','2019-02-14 10:01:58'),(14,1,'登录后台管理系统','2019-02-15 10:30:43'),(15,1,'登录后台管理系统','2019-02-15 15:37:35'),(16,1,'登录后台管理系统','2019-02-16 02:06:33'),(17,1,'登录后台管理系统','2019-02-18 08:36:33'),(18,1,'登录后台管理系统','2019-02-18 10:14:38'),(19,1,'登录后台管理系统','2019-02-19 07:30:58'),(20,1,'登录后台管理系统','2019-02-20 03:04:23'),(21,1,'登录后台管理系统','2019-02-20 03:25:54'),(22,1,'登录后台管理系统','2019-02-20 04:06:30'),(23,1,'登录后台管理系统','2019-02-21 02:26:08'),(24,1,'登录后台管理系统','2019-02-22 02:03:55');
/*!40000 ALTER TABLE `log_` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `manager`
--

DROP TABLE IF EXISTS `manager`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `manager` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `salt` varchar(255) DEFAULT NULL,
  `nickName` varchar(255) DEFAULT NULL,
  `sex` int(2) DEFAULT NULL,
  `mobile` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `status_` int(2) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `manager`
--

LOCK TABLES `manager` WRITE;
/*!40000 ALTER TABLE `manager` DISABLE KEYS */;
INSERT INTO `manager` VALUES (1,'admin','1a002719582de2f1fb32deaadacf2444','KDXqypkL9K9RgBQpMu0xlA==','Galler',0,'123456789','953625619@qq.com','2018-11-07 21:18:00',1);
/*!40000 ALTER TABLE `manager` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `manager_role`
--

DROP TABLE IF EXISTS `manager_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `manager_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `mid` int(11) DEFAULT NULL,
  `rid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `manager_role`
--

LOCK TABLES `manager_role` WRITE;
/*!40000 ALTER TABLE `manager_role` DISABLE KEYS */;
INSERT INTO `manager_role` VALUES (1,1,1);
/*!40000 ALTER TABLE `manager_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `member`
--

DROP TABLE IF EXISTS `member`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `member` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `min` int(11) DEFAULT NULL,
  `max` int(11) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `score` int(11) DEFAULT NULL,
  `icon` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `member`
--

LOCK TABLES `member` WRITE;
/*!40000 ALTER TABLE `member` DISABLE KEYS */;
INSERT INTO `member` VALUES (1,50,100,'vip1',0,'#8B00FF'),(2,101,200,'vip2',0,'#00F'),(3,201,350,'vip3',0,'#0FF'),(4,351,550,'vip4',0,'#0F0'),(5,551,800,'vip5',0,'#FF0'),(6,801,1100,'vip6',0,'#FF7F00'),(7,1101,1500,'vip7',0,'#F00');
/*!40000 ALTER TABLE `member` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `message`
--

DROP TABLE IF EXISTS `message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `message` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uid` int(11) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `text` mediumtext,
  `reply` mediumtext,
  `replyDate` datetime DEFAULT NULL,
  `status` int(2) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `message`
--

LOCK TABLES `message` WRITE;
/*!40000 ALTER TABLE `message` DISABLE KEYS */;
INSERT INTO `message` VALUES (1,1,'2018-10-10 10:00:00','测试一下',NULL,NULL,1,NULL);
/*!40000 ALTER TABLE `message` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `module`
--

DROP TABLE IF EXISTS `module`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `module` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  `desc_` varchar(100) DEFAULT NULL,
  `pid` int(11) DEFAULT NULL,
  `url` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `module`
--

LOCK TABLES `module` WRITE;
/*!40000 ALTER TABLE `module` DISABLE KEYS */;
INSERT INTO `module` VALUES (1,'backModule','后台管理',0,'/admin'),(2,'articleModule','文章展示',1,'/article'),(3,'writingModule','创建文章',1,'/writing'),(4,'categoryModule','分类管理',1,'/category'),(5,'tagModule','标签管理',1,'/tag'),(6,'commentModule','评论管理',1,'/comment'),(7,'messageModule','留言管理',1,'/message'),(8,'userModule','用户管理',1,'/user'),(9,'memberModule','会员管理',1,'/member'),(10,'scoreModule','积分管理',1,'/score'),(11,'carouselModule','轮播图管理',1,'/carousel'),(12,'linkModule','链接管理',1,'/link'),(13,'noticeModule','公告管理',1,'/notice'),(14,'optionModule','网站管理',1,'/option'),(15,'managerModule','管理员管理',1,'/manager'),(16,'roleModule','角色管理',1,'/role'),(17,'moduleModule','模块管理',1,'/module'),(18,'operationModule','操作管理',1,'/operation');
/*!40000 ALTER TABLE `module` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `msg`
--

DROP TABLE IF EXISTS `msg`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `msg` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `text` mediumtext,
  `createDate` datetime DEFAULT NULL,
  `status_` int(2) DEFAULT '0',
  `sid` int(11) NOT NULL,
  `rid` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `msg`
--

LOCK TABLES `msg` WRITE;
/*!40000 ALTER TABLE `msg` DISABLE KEYS */;
/*!40000 ALTER TABLE `msg` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `notice`
--

DROP TABLE IF EXISTS `notice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `notice` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) DEFAULT NULL,
  `text` mediumtext,
  `createDate` datetime DEFAULT NULL,
  `updateDate` datetime DEFAULT NULL,
  `status_` int(2) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notice`
--

LOCK TABLES `notice` WRITE;
/*!40000 ALTER TABLE `notice` DISABLE KEYS */;
INSERT INTO `notice` VALUES (1,'大家好','欢迎大家来到我的博客网来做客','2018-10-10 10:00:00','2019-02-13 08:32:58',0);
/*!40000 ALTER TABLE `notice` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `operation`
--

DROP TABLE IF EXISTS `operation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `operation` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  `desc_` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `operation`
--

LOCK TABLES `operation` WRITE;
/*!40000 ALTER TABLE `operation` DISABLE KEYS */;
INSERT INTO `operation` VALUES (1,'create','增加'),(2,'delete','删除'),(3,'update','修改'),(4,'read','获取');
/*!40000 ALTER TABLE `operation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `option_`
--

DROP TABLE IF EXISTS `option_`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `option_` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `key_` varchar(255) DEFAULT NULL,
  `value_` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `option_`
--

LOCK TABLES `option_` WRITE;
/*!40000 ALTER TABLE `option_` DISABLE KEYS */;
INSERT INTO `option_` VALUES (1,'name','浩说'),(2,'describe','爱分享，爱创造，just coding'),(3,'right','©2018-2020 linhao 版权所有'),(4,'key','站,长,真,酷');
/*!40000 ALTER TABLE `option_` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `permission`
--

DROP TABLE IF EXISTS `permission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `mid` int(11) DEFAULT NULL,
  `oid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=73 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `permission`
--

LOCK TABLES `permission` WRITE;
/*!40000 ALTER TABLE `permission` DISABLE KEYS */;
INSERT INTO `permission` VALUES (1,1,1),(2,1,2),(3,1,3),(4,1,4),(5,2,1),(6,2,2),(7,2,3),(8,2,4),(9,3,1),(10,3,2),(11,3,3),(12,3,4),(13,4,1),(14,4,2),(15,4,3),(16,4,4),(17,5,1),(18,5,2),(19,5,3),(20,5,4),(21,6,1),(22,6,2),(23,6,3),(24,6,4),(25,7,1),(26,7,2),(27,7,3),(28,7,4),(29,8,1),(30,8,2),(31,8,3),(32,8,4),(33,9,1),(34,9,2),(35,9,3),(36,9,4),(37,10,1),(38,10,2),(39,10,3),(40,10,4),(41,11,1),(42,11,2),(43,11,3),(44,11,4),(45,12,1),(46,12,2),(47,12,3),(48,12,4),(49,13,1),(50,13,2),(51,13,3),(52,13,4),(53,14,1),(54,14,2),(55,14,3),(56,14,4),(57,15,1),(58,15,2),(59,15,3),(60,15,4),(61,16,1),(62,16,2),(63,16,3),(64,16,4),(65,17,1),(66,17,2),(67,17,3),(68,17,4),(69,18,1),(70,18,2),(71,18,3),(72,18,4);
/*!40000 ALTER TABLE `permission` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `power`
--

DROP TABLE IF EXISTS `power`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `power` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `mid` int(11) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `text` mediumtext,
  `score` int(11) DEFAULT NULL,
  `exchange` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `power`
--

LOCK TABLES `power` WRITE;
/*!40000 ALTER TABLE `power` DISABLE KEYS */;
/*!40000 ALTER TABLE `power` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `desc_` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (1,'root','超级管理员'),(2,'adminManager','网站管理员'),(3,'blogMagager','博客管理员'),(4,'articleMagager','文章管理员'),(5,'categoryManager','分类管理员'),(6,'tagManager','标签管理员'),(7,'msgManager','信息管理员'),(8,'commentManager','评论管理员'),(9,'messageManager','留言管理员'),(10,'userManager','用户管理员'),(11,'memberManager','会员管理员'),(12,'scoreManager','积分管理员'),(13,'webManager','网站建设管理员'),(14,'carouselManager','轮播图管理员'),(15,'linkManager','链接管理员'),(16,'noticeManager','公告管理员'),(17,'systemManager','系统管理员'),(18,'optionManager','网站管理员'),(19,'managerManager','管理员管理员'),(20,'roleManager','角色管理员'),(21,'moduleManager','模块管理员'),(22,'operationManager','操作管理员');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role_permission`
--

DROP TABLE IF EXISTS `role_permission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `role_permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `rid` int(11) DEFAULT NULL,
  `pid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=329 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role_permission`
--

LOCK TABLES `role_permission` WRITE;
/*!40000 ALTER TABLE `role_permission` DISABLE KEYS */;
INSERT INTO `role_permission` VALUES (1,1,1),(2,1,2),(3,1,3),(4,1,4),(5,1,5),(6,1,6),(7,1,7),(8,1,8),(9,1,9),(10,1,10),(11,1,11),(12,1,12),(13,1,13),(14,1,14),(15,1,15),(16,1,16),(17,1,17),(18,1,18),(19,1,19),(20,1,20),(21,1,21),(22,1,22),(23,1,23),(24,1,24),(25,1,25),(26,1,26),(27,1,27),(28,1,28),(29,1,29),(30,1,30),(31,1,31),(32,1,32),(33,1,33),(34,1,34),(35,1,35),(36,1,36),(37,1,37),(38,1,38),(39,1,39),(40,1,40),(41,1,41),(42,1,42),(43,1,43),(44,1,44),(45,1,45),(46,1,46),(47,1,47),(48,1,48),(49,1,49),(50,1,50),(51,1,51),(52,1,52),(53,1,53),(54,1,54),(55,1,55),(56,1,56),(57,1,57),(58,1,58),(59,1,59),(60,1,60),(61,1,61),(62,1,62),(63,1,63),(64,1,64),(65,1,65),(66,1,66),(67,1,67),(68,1,68),(69,1,69),(70,1,70),(71,1,71),(72,1,72),(73,2,1),(74,2,2),(75,2,3),(76,2,4),(77,2,5),(78,2,6),(79,2,7),(80,2,8),(81,2,9),(82,2,10),(83,2,11),(84,2,12),(85,2,13),(86,2,14),(87,2,15),(88,2,16),(89,2,17),(90,2,18),(91,2,19),(92,2,20),(93,2,21),(94,2,22),(95,2,23),(96,2,24),(97,2,25),(98,2,26),(99,2,27),(100,2,28),(101,2,29),(102,2,30),(103,2,31),(104,2,32),(105,2,33),(106,2,34),(107,2,35),(108,2,36),(109,2,37),(110,2,38),(111,2,39),(112,2,40),(113,2,41),(114,2,42),(115,2,43),(116,2,44),(117,2,45),(118,2,46),(119,2,47),(120,2,48),(121,2,49),(122,2,50),(123,2,51),(124,2,52),(125,3,1),(126,3,2),(127,3,3),(128,3,4),(129,3,5),(130,3,6),(131,3,7),(132,3,8),(133,3,9),(134,3,10),(135,3,11),(136,3,12),(137,3,13),(138,3,14),(139,3,15),(140,3,16),(141,3,17),(142,3,18),(143,3,19),(144,3,20),(145,4,1),(146,4,2),(147,4,3),(148,4,4),(149,4,5),(150,4,6),(151,4,7),(152,4,8),(153,4,9),(154,4,10),(155,4,11),(156,4,12),(157,5,1),(158,5,2),(159,5,3),(160,5,4),(161,5,13),(162,5,14),(163,5,15),(164,5,16),(165,6,1),(166,6,2),(167,6,3),(168,6,4),(169,6,17),(170,6,18),(171,6,19),(172,6,20),(173,7,1),(174,7,2),(175,7,3),(176,7,4),(177,7,21),(178,7,22),(179,7,23),(180,7,24),(181,7,25),(182,7,26),(183,7,27),(184,7,28),(185,8,1),(186,8,2),(187,8,3),(188,8,4),(189,8,21),(190,8,22),(191,8,23),(192,8,24),(193,9,1),(194,9,2),(195,9,3),(196,9,4),(197,9,25),(198,9,26),(199,9,27),(200,9,28),(201,10,1),(202,10,2),(203,10,3),(204,10,4),(205,10,29),(206,10,30),(207,10,31),(208,10,32),(209,11,1),(210,11,2),(211,11,3),(212,11,4),(213,11,33),(214,11,34),(215,11,35),(216,11,36),(217,12,1),(218,12,2),(219,12,3),(220,12,4),(221,12,37),(222,12,38),(223,12,39),(224,12,40),(225,13,1),(226,13,2),(227,13,3),(228,13,4),(229,13,41),(230,13,42),(231,13,43),(232,13,44),(233,13,45),(234,13,46),(235,13,47),(236,13,48),(237,13,49),(238,13,50),(239,13,51),(240,13,52),(241,14,1),(242,14,2),(243,14,3),(244,14,4),(245,14,41),(246,14,42),(247,14,43),(248,14,44),(249,15,1),(250,15,2),(251,15,3),(252,15,4),(253,15,45),(254,15,46),(255,15,47),(256,15,48),(257,16,1),(258,16,2),(259,16,3),(260,16,4),(261,16,49),(262,16,50),(263,16,51),(264,16,52),(265,17,1),(266,17,2),(267,17,3),(268,17,4),(269,17,53),(270,17,54),(271,17,55),(272,17,56),(273,17,57),(274,17,58),(275,17,59),(276,17,60),(277,17,61),(278,17,62),(279,17,63),(280,17,64),(281,17,65),(282,17,66),(283,17,67),(284,17,68),(285,17,69),(286,17,70),(287,17,71),(288,17,72),(289,18,1),(290,18,2),(291,18,3),(292,18,4),(293,18,53),(294,18,54),(295,18,55),(296,18,56),(297,19,1),(298,19,2),(299,19,3),(300,19,4),(301,19,57),(302,19,58),(303,19,59),(304,19,60),(305,20,1),(306,20,2),(307,20,3),(308,20,4),(309,20,61),(310,20,62),(311,20,63),(312,20,64),(313,21,1),(314,21,2),(315,21,3),(316,21,4),(317,21,65),(318,21,66),(319,21,67),(320,21,68),(321,22,1),(322,22,2),(323,22,3),(324,22,4),(325,22,69),(326,22,70),(327,22,71),(328,22,72);
/*!40000 ALTER TABLE `role_permission` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `start`
--

DROP TABLE IF EXISTS `start`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `start` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uid` int(11) NOT NULL,
  `aid` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `start`
--

LOCK TABLES `start` WRITE;
/*!40000 ALTER TABLE `start` DISABLE KEYS */;
INSERT INTO `start` VALUES (1,2,4);
/*!40000 ALTER TABLE `start` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tag`
--

DROP TABLE IF EXISTS `tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tag` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `status_` int(2) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tag`
--

LOCK TABLES `tag` WRITE;
/*!40000 ALTER TABLE `tag` DISABLE KEYS */;
INSERT INTO `tag` VALUES (1,'音乐工具',0),(2,'视频工具',0),(3,'图片工具',0),(4,'多媒体',0),(5,'个人收藏',0),(6,'破解工具',0),(7,'通讯工具',0),(8,'下载器',0),(9,'生活服务',0),(10,'搜索工具',0),(11,'美化工具',0),(12,'提高效率',0),(13,'格式转换',0),(14,'浏览器',0),(15,'文档处理',0),(16,'数据恢复',0),(17,'系统安全',0),(18,'私密神器',0);
/*!40000 ALTER TABLE `tag` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tag_article`
--

DROP TABLE IF EXISTS `tag_article`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tag_article` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tid` int(11) NOT NULL,
  `aid` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tag_article`
--

LOCK TABLES `tag_article` WRITE;
/*!40000 ALTER TABLE `tag_article` DISABLE KEYS */;
INSERT INTO `tag_article` VALUES (1,12,1),(5,12,4),(10,1,5),(11,2,6),(12,2,7),(13,18,8),(14,9,8),(15,12,2),(16,18,3),(17,12,3),(20,2,9),(21,12,10),(24,18,11),(25,12,11),(26,8,12),(28,8,13),(29,12,14);
/*!40000 ALTER TABLE `tag_article` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `nickName` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `salt` varchar(255) DEFAULT NULL,
  `sex` int(2) DEFAULT NULL,
  `registerDate` datetime DEFAULT NULL,
  `loginDate` datetime DEFAULT NULL,
  `mobile` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `img` varchar(255) DEFAULT NULL,
  `mid` int(11) DEFAULT NULL,
  `score` int(11) DEFAULT '0',
  `status_` int(2) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'galler','test','b3432791463c1e94d9d6c7d064b5bbc7','l8p55F5BvsndSmzPoq3iMA==',0,'2018-11-07 21:18:00','2019-02-15 10:18:21','123456789','123456@163.com','/image/profile_user/7.jpg',0,0,0),(2,'test123','test','57f5f354513513a638cac3438e62cb0c','jLu1asC4mBh1StF4mihN3Q==',0,'2019-02-15 10:21:40','2019-02-18 04:26:08','13286441829','953625619@qq.com','/image/profile_user/11.jpg',0,20,0);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-02-23 16:12:16
