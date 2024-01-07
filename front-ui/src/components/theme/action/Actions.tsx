import { Button, CircularProgress, Icon } from '@mui/material';
import React from 'react';
import { Link } from 'react-router-dom';
import {
  ActionButtonProps,
  ActionContainerProps,
  ActionLinkProps,
} from '../../../lib/plume-admin-theme/action/ActionProps';

export function ActionsContainer({
  children,
  actionStyle,
}: ActionContainerProps) {
  return (
    <div className={`actions ${actionStyle ?? ''}`}>
      {children}
    </div>
  );
}

export function ActionLink({
  actionStyle, icon, linkTo, children,
}: ActionLinkProps) {
  return (
    <Button
      className={`action-container ${actionStyle}`}
      variant="contained"
      color={actionStyle}
      component={Link}
      to={linkTo}
      startIcon={icon && <Icon>{icon}</Icon>}
    >
      {children}
    </Button>
  );
}

export function ActionButton({
  actionStyle,
  icon,
  cssClasses,
  onClick,
  isLoading,
  children,
}: ActionButtonProps) {
  return (
    <div
      className={
        `action-container loading-button ${cssClasses ?? ''}${isLoading ? ' loading-button--loading' : ''}`
      }
    >
      <Button
        onClick={onClick}
        type={onClick ? 'button' : 'submit'}
        variant="contained"
        disabled={isLoading}
        color={actionStyle}
        startIcon={icon && <Icon>{icon}</Icon>}
      >
        {children}
      </Button>
      {
        isLoading
        && (
          <div className="loading-progress">
            <CircularProgress size="100%" color="inherit" />
          </div>
        )
      }
    </div>
  );
}
