import React, { useState } from 'react';
import PersonIcon from '@mui/icons-material/Person';
import Avatar from '@mui/material/Avatar/Avatar';
import Tooltip from '@mui/material/Tooltip';
import IconButton from '@mui/material/IconButton';
import Menu from '@mui/material/Menu';
import { getGlobalInstance } from 'plume-ts-di';
import { ListItemIcon, MenuItem } from '@mui/material';
import {
  AppRegistration, Login, Logout, Portrait,
} from '@mui/icons-material';
import { useObservable } from 'micro-observables';
import { NavigateFunction, useNavigate } from 'react-router-dom';
import SessionService from '../../services/session/SessionService';
import useMessages from '../../i18n/hooks/messagesHook';
import { LOGIN, PROFILE, REGISTER } from '../Routes';
import { UserWithExpiration } from '../../services/session/User';

export default function AccountIcon() {
  const { messages } = useMessages();
  const navigate: NavigateFunction = useNavigate();
  const sessionService: SessionService = getGlobalInstance(SessionService);
  const user: UserWithExpiration | undefined = useObservable(sessionService.getCurrentUser());
  const isAuthenticated: boolean = useObservable(sessionService.isAuthenticated());
  const [open, setOpen] = useState<boolean>(false);
  const [anchorEl, setAnchorEl] = React.useState<null | HTMLElement>(null);

  const disconnect = () => {
    sessionService.disconnect();
    navigate(LOGIN);
  };

  const goToProfile = () => {
    navigate(PROFILE);
  };

  const onMenuClick = (event: React.MouseEvent<HTMLElement>) => {
    setAnchorEl(event.currentTarget);
    setOpen(true);
  };

  const onClose = () => {
    setOpen(false);
  };

  return (
    <div className="account-icon">
      <Tooltip title={messages.menu.account}>
        <IconButton
          onClick={onMenuClick}
          size="small"
          sx={{ ml: 2 }}
          aria-controls={open ? 'account-menu' : undefined}
          aria-haspopup="true"
          aria-expanded={open ? 'true' : undefined}
        >
          <Avatar sx={{ width: 32, height: 32 }}>
            {user && isAuthenticated
              ? <>{user.userName.charAt(0)}</>
              : <PersonIcon/>
            }
          </Avatar>
        </IconButton>
      </Tooltip>
      {open && <Menu
        anchorEl={anchorEl}
        id="account-menu"
        open={open}
        onClose={onClose}
        onClick={onClose}
        transformOrigin={{ horizontal: 'right', vertical: 'top' }}
        anchorOrigin={{ horizontal: 'right', vertical: 'bottom' }}
      >
        {isAuthenticated
          ? [
            <MenuItem onClick={goToProfile} key="profile">
              <ListItemIcon>
                <Portrait fontSize="small" />
              </ListItemIcon>
              {messages.action.profile}
            </MenuItem>,
            <MenuItem onClick={disconnect} key="logout">
              <ListItemIcon>
                <Logout fontSize="small" />
              </ListItemIcon>
              {messages.action.disconnect}
            </MenuItem>,
          ]
          : [
            <MenuItem onClick={() => navigate(LOGIN)} key="login">
              <ListItemIcon>
                <Login fontSize="small" />
              </ListItemIcon>
              {messages.action.authenticate}
            </MenuItem>,
            <MenuItem onClick={() => navigate(REGISTER)} key="register">
              <ListItemIcon>
                <AppRegistration fontSize="small" />
              </ListItemIcon>
              {messages.action.register}
            </MenuItem>,
          ]
        }
        </Menu>
      }
      </div>
  );
}
