import { ReactNode } from 'react';

export type ActionContainerProps = {
  actionStyle?: 'inherit' | 'primary' | 'secondary' | 'error',
  children?: React.ReactNode,
};

export type ActionProps = {
  actionStyle?: 'inherit' | 'primary' | 'secondary' | 'error',
  icon?: ReactNode,
  cssClasses?: string,
  children?: React.ReactNode,
};

export interface ActionLinkProps extends ActionProps {
  linkTo: string,
}

export interface ActionButtonProps extends ActionProps {
  onClick?: () => void,
  isLoading?: boolean,
}
