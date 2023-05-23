import React from 'react';
import * as VscIcons from 'react-icons/vsc';
import * as AiIcons from 'react-icons/ai';
import * as ImIcons from 'react-icons/im';

export const SidebarData = [
  {
    title: 'My Home',
    path: '/myhome',
    icon: <AiIcons.AiOutlineHome />,
    cName: 'nav-text'
  },
  {
    title: 'My Organizations',
    path: '/myorganizations',
    icon: <VscIcons.VscOrganization />,
    cName: 'nav-text'
  },
  {
    title: 'My Projects',
    path: '/myprojects',
    icon: <AiIcons.AiOutlineProject />,
    cName: 'nav-text'
  },
  {
    title: 'All Organizations',
    path: '/allorganizations',
    icon: <ImIcons.ImSphere />,
    cName: 'nav-text'
  },
  {
    title: 'Mail',
    path: '/mail',
    icon: <AiIcons.AiOutlineMail />,
    cName: 'nav-text'
  }
];