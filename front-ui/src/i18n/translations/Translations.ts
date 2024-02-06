import { Dayjs } from 'dayjs';

export interface ErrorFunction {
  (...args: string[]): string,
}

export type Translations = {
  app: {
    name: string,
  },
  // actions
  action: {
    back: string,
    cancel: string,
    save: string,
    delete: string,
    search: string,
    add: string,
    authenticate: string,
    register: string,
    resend: string,
    change_password: string,
    reset_password: string,
    disconnect: string,
    keep_editing: string,
    close_without_saving: string,
    profile: string,
    delete_account: string,
    confirm_password: string,
    account_will_be_deleted: string,
    select: string,
    share: string,
    copied: string,
    make_own_pronostics: string,
    go_home: string,
  },
  // common labels
  label: {
    creation_date: string,
    loading: string,
  },
  // common messages
  message: {
    changes_saved: string,
    unsaved_data: string,
    confirm_delete: string,
  },
  // navigation
  nav: {
    home: string,
    users: string,
    user_list: string,
  },
  // home
  home: {
    title: string,
    pronostic: string,
    in: (date: Dayjs) => string,
  },
  login: {
    title: string,
  },
  register: {
    title: string,
    creationOK: string,
    validationOk: string,
    resetOk: string,
    resendOk: string,
    changeOk: string,
    deletedOk: string,
    password_forgotten: string,
  },
  // users
  users: {
    userName: string,
    password_actual: string,
    password_new: string,
    password: string,
    verify: string,
    email: string,
    firstName: string,
    lastName: string,
    role: string,
    rgpd: string,
  },
  rules: {
    minLength: (size: number, field: string) => string,
    maxLength: (size: number, field: string) => string,
    required: (field: string) => string,
    different: string,
    rgpd: string,
    maxLengthNoField: (size: number) => string,
    requiredNoField: string,
  },
  // errors
  error: {
    field : {
      required: string,
      email_wrong_format: string,
    },
  },
  menu: {
    account: string,
  },

  footer: {
    legal: string,
    rgpd: string,
  },

  slider: {
    previous: string,
    next: string,
  },
  titles: {
    verify_email: string,
    change_password: string,
    reset_password: string,
    account: string,
  },

  'http-errors': {
    INTERNAL_ERROR: string,
    NETWORK_ERROR: string,
    TIMEOUT_ERROR: string,
    FORBIDDEN_ERROR: string,
    WRONG_LOGIN_OR_PASSWORD: string,
    TOO_MANY_WRONG_ATTEMPTS: (seconds: string) => string,
    FIELD_REQUIRED: (fieldName: string) => string,
    EMAIL_ALREADY_EXISTS: string,
    MESSAGE: (message: string) => string,
    ACCOUNT_NOT_VALIDATED: string,
    VERIFICATION_UNKNOWN: string,
    PASSWORD_MODIFICATION_UNKNOWN: string,
    CANNOT_AUTHENTICATE_THROUGH_GOOGLE: string,
    RECAPTCHA_ERROR: string,
    INVALID_PASSWORD: string,
    SHARE_NOT_FOUND: string,
  },
  google: {
    recaptcha: string,
  },
  ceremony: {
    selected: string,
    yourChoice: (title: string | undefined) => string,
  },
  'share-modal': {
    title: string,
    description: string,
    content_not_connected: string,
    content_connected: string,
  },
};
