import dayjs, { Dayjs } from 'dayjs';
import { Translations } from './Translations';

const frMessages: Translations = {
  app: {
    name: 'Rank-It',
  },
  // actions
  action: {
    back: 'Retour',
    cancel: 'Annuler',
    save: 'Enregistrer',
    delete: 'Supprimer',
    search: 'Rechercher',
    add: 'Ajouter',
    authenticate: 'Me connecter',
    register: 'Créer mon compte',
    resend: 'Renvoyer un lien',
    change_password: 'Modifier mon mot de passe',
    reset_password: 'Réinitialiser mon mot de passe',
    disconnect: 'Me déconnecter',
    keep_editing: 'Rester sur la page',
    close_without_saving: 'Fermer sans sauvegarder',
  },
  // common labels
  label: {
    creation_date: 'Date de création',
    loading: 'Chargement...',
  },
  // common messages
  message: {
    changes_saved: 'Les modifications ont bien été enregistrées',
    unsaved_data: 'Des modifications n\'ont pas été enregistrées. '
      + 'Si vous voulez enregistrer ces modifications, cliquez sur le bouton "Rester sur la page"',
    confirm_delete: 'Pour confirmer la suppression, cliquez sur le bouton "Supprimer"',
  },
  // navigation
  nav: {
    home: 'Accueil',
    users: 'Gestion des utilisateurs',
    user_list: 'Utilisateurs',
  },
  // home
  home: {
    title: 'Page d\'accueil',
    pronostic: 'Voir la cérémonie',
    in: (date: Dayjs) => {
      const diff: number = date.diff(dayjs(), 'days');
      if (diff > 0) {
        return `Dans ${diff} jours`;
      }
      return 'Aujourd\'hui';
    },
  },
  login: {
    title: 'Se connecter',
  },
  register: {
    title: 'Créer un compte',
    creationOK: 'Votre compte a bien été créé ! \n'
      + 'Vous avez reçu un email de confirmation, cliquez sur le lien dans l\'email '
      + 'puis connectez vous.',
    validationOk: 'Votre compte est validé, connectez vous !',
    resendOk: 'Si votre compte existe et que vous n\'avez pas de lien de vérification actif,'
      + '\n vous allez recevoir un mail',
    resetOk: 'Si votre compte existe et que vous n\'avez pas de lien de modification de mot de passe actif,'
      + '\n vous allez recevoir un mail',
    changeOk: 'Votre mot de passe a été modifié',
    password_forgotten: 'Mot de passe oublié ?',
  },
  // users
  users: {
    userName: 'Nom d\'utilisateur',
    password: 'Mot de passe',
    verify: 'Vérification du mot de passe',
    email: 'E-mail',
    firstName: 'Prénom',
    lastName: 'Nom',
    role: 'Rôle',
    rgpd: 'J\'ai lu et j\'accepte la politique RGPD',
  },
  rules: {
    minLength: (size: number, field: string) => `le champ ${field} doit faire plus de ${size} caractères`,
    maxLength: (size: number, field: string) => `le champ ${field} doit faire moins de ${size} caractères`,
    required: (field: string) => `le champ ${field} est obligatoire`,
    different: 'Les champs "mot de passe" et "confirmation du mot de passe" doivent être identiques',
    rgpd: 'Veuillez accepter la politique rgpd',
  },
  // errors
  error: {
    field: {
      required: 'Le champ est requis',
      email_wrong_format: 'L\'adresse e-mail saisie semble être incorrecte',
    },
  },
  menu: {
    account: 'Compte',
  },
  footer: {
    legal: 'Mentions Légales',
    rgpd: 'Politique RGPD',
  },
  titles: {
    verify_email: 'Vérification du compte',
    change_password: 'Changement de mot de passe',
    reset_password: 'Mot de passe oublié',
  },
  'http-errors': {
    INTERNAL_ERROR: 'Une erreur inattendue s\'est produite',
    NETWORK_ERROR: 'Erreur réseau, votre connexion internet semble indisponible',
    TIMEOUT_ERROR: 'Erreur réseau (timeout), votre connexion internet ou le serveur distant semble indisponible',
    FORBIDDEN_ERROR: 'Il semble que vous n\'avez pas accès à cette ressource ou à cette action',
    WRONG_LOGIN_OR_PASSWORD: 'Nom d\'utilisateur ou mot de passe incorrect',
    // eslint-disable-next-line max-len
    TOO_MANY_WRONG_ATTEMPTS: (seconds: string) => `Suite à des erreurs dans la saisie de vos identifiants, votre compte est verrouillé pendant ${seconds} secondes, veuillez-vous reconnecter ultérieurement`,
    FIELD_REQUIRED: (fieldName: string) => `Le champ '${fieldName}' est requis`,
    EMAIL_ALREADY_EXISTS: 'Cet email existe déjà',
    MESSAGE: (message: string) => message,
    ACCOUNT_NOT_VALIDATED: 'Validez votre compte via l\'email '
      + 'que vous avez reçu avant d\'utiliser cette fonctionnalité',
    VERIFICATION_UNKNOWN: 'Ce lien de vérification est '
      + 'inconnu ou bien expiré vous pouvez en redemander un ici',
    PASSWORD_MODIFICATION_UNKNOWN: 'Ce lien de réinitialisation est '
      + 'inconnu ou bien expiré',
  },
} as const;

export default frMessages;
