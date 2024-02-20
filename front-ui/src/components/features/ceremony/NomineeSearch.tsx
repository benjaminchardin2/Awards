import Card from '@mui/material/Card';
import CardContent from '@mui/material/CardContent';
import CardHeader from '@mui/material/CardHeader';
import React, { SyntheticEvent, useState } from 'react';
import Autocomplete, { AutocompleteRenderInputParams } from '@mui/material/Autocomplete';
import TextField from '@mui/material/TextField';
import CircularProgress from '@mui/material/CircularProgress';
import { getGlobalInstance } from 'plume-ts-di';
import { Logger } from 'simple-logging-system';
import { HttpError } from 'simple-http-rest-client';
import { debounce } from '@mui/material/utils';
import CardMedia from '@mui/material/CardMedia';
import FavoriteIcon from '@mui/icons-material/Favorite';
import CardActions from '@mui/material/CardActions';
import List from '@mui/material/List';
import ListItem from '@mui/material/ListItem';
import ListItemButton from '@mui/material/ListItemButton';
import ListItemText from '@mui/material/ListItemText';
import Typography from '@mui/material/Typography';
import ToggleButton from '@mui/material/ToggleButton';
import ToggleButtonGroup from '@mui/material/ToggleButtonGroup';
import Button from '@mui/material/Button';
import useMessages from '../../../i18n/hooks/messagesHook';
import SearchApi, { AdditionalResults, SearchResult } from '../../../api/search/SearchApi';
import useLoader, { LoaderState } from '../../../lib/plume-http-react-hook-loader/promiseLoaderHook';
import { NomineeType } from '../../../api/ceremony/CeremonyApi';
import { useOnDependenciesChange } from '../../../lib/react-hooks-alias/ReactHooksAlias';
import { ActionButton, ActionsContainer } from '../../theme/action/Actions';

type NomineeSearchProps = {
  onClick: (nominee: NomineeType) => void,
  awardType: 'MOVIE' | 'CAST' | 'CREW',
  awardId: number,
  nominee?: NomineeType,
  isSelected?: boolean,
  skipChoice: (awardId: string) => void,
};

const logger: Logger = new Logger('NomineeSearch');

export default function NomineeSearch({
  onClick, awardType, awardId, nominee, skipChoice,
}: NomineeSearchProps) {
  const { messages } = useMessages();
  const loader: LoaderState = useLoader();
  const searchApi: SearchApi = getGlobalInstance(SearchApi);
  const [searchType, setSearchType] = useState<'MOVIES' | 'PERSONS'>('MOVIES');
  const [movie, setMovie] = useState<SearchResult | undefined | null>();
  const [person, setPerson] = useState<SearchResult | undefined | null>();
  const [open, setOpen] = useState<boolean>(false);
  const [nomineeResult, setNomineeResult] = useState<NomineeType | undefined>(nominee);
  const [options, setOptions] = useState<SearchResult[]>([]);
  const [additionalResults, setAdditionalResults] = useState<SearchResult[]>([]);
  const [additionalResultNextPage, setAdditionalResultNextPage] = useState<number>(0);
  const [hasMoreResult, setHasMoreResult] = useState<boolean>(false);

  const search = (value: string) => {
    if (value) {
      if (searchType === 'MOVIES') {
        searchApi
          .searchMovies(value, 0)
          .then((results: SearchResult[]) => setOptions(results))
          .catch((e: HttpError) => {
            logger.error('Error occured while searching for movies', e);
            setOptions([]);
          });
      } else {
        searchApi
          .searchPersons(value, 0)
          .then((results: SearchResult[]) => setOptions(results))
          .catch((e: HttpError) => {
            logger.error('Error occured while searching for movies', e);
            setOptions([]);
          });
      }
    }
  };

  const onAutocompleteChoice = (_: SyntheticEvent<Element, Event>, value: SearchResult | null) => {
    if (searchType === 'MOVIES') {
      setMovie(value);
    } else {
      setPerson(value);
    }
    if (!value) {
      setAdditionalResults([]);
    }
  };

  const onAdditionalResultChoice = (value: SearchResult) => {
    if (searchType === 'MOVIES') {
      setPerson(value);
    } else {
      setMovie(value);
    }
  };

  const handleChange = (_: React.MouseEvent<HTMLElement>, nextType: 'MOVIES' | 'PERSONS' | undefined) => {
    if (nextType) {
      setSearchType(nextType);
    } else if (searchType === 'MOVIES') {
      setSearchType('PERSONS');
    } else {
      setSearchType('MOVIES');
    }
  };

  const loadMoreResults = () => {
    if (movie) {
      searchApi.searchMoviePersons(awardId, additionalResultNextPage, movie.id)
        .then((additionalInfo: AdditionalResults) => {
          setAdditionalResults([...additionalResults, ...additionalInfo.searchResults]);
          setHasMoreResult(additionalInfo.hasMoreResults);
          setAdditionalResultNextPage(additionalInfo.nextPage);
        })
        .catch((e: HttpError) => {
          logger.error('Error occured while searching for movie nominees', e);
          setAdditionalResults([]);
        });
    } else if (person) {
      searchApi.searchPersonCredits(awardId, additionalResultNextPage, person.id)
        .then((additionalInfo: AdditionalResults) => {
          setAdditionalResults([...additionalResults, ...additionalInfo.searchResults]);
          setHasMoreResult(additionalInfo.hasMoreResults);
          setAdditionalResultNextPage(additionalInfo.nextPage);
        })
        .catch((e: HttpError) => {
          logger.error('Error occured while searching for person credits', e);
          setAdditionalResults([]);
        });
    }
  };

  useOnDependenciesChange(() => {
    if (movie && awardType === 'MOVIE') {
      searchApi
        .convertSearchToNominee({
          movieId: movie.id,
          awardsType: awardType,
        })
        .then((nomineeFromApi: NomineeType) => {
          setNomineeResult(nomineeFromApi);
          onClick(nomineeFromApi);
        })
        .catch((e: HttpError) => {
          logger.error('Error occured while converting to nominee', e);
          setNomineeResult(undefined);
        });
    } else if (movie && person) {
      searchApi
        .convertSearchToNominee({
          movieId: movie.id,
          personId: person.id,
          awardsType: awardType,
        })
        .then((nomineeFromApi: NomineeType) => {
          setNomineeResult(nomineeFromApi);
          onClick(nomineeFromApi);
        })
        .catch((e: HttpError) => {
          logger.error('Error occured while converting to nominee', e);
          setNomineeResult(undefined);
        });
    } else if (movie) {
      searchApi.searchMoviePersons(awardId, 0, movie.id)
        .then((additionalInfo: AdditionalResults) => {
          setAdditionalResults(additionalInfo.searchResults);
          setHasMoreResult(additionalInfo.hasMoreResults);
          setAdditionalResultNextPage(additionalInfo.nextPage);
        })
        .catch((e: HttpError) => {
          logger.error('Error occured while searching for movie nominees', e);
          setAdditionalResults([]);
        });
    } else if (person) {
      searchApi.searchPersonCredits(awardId, 0, person.id)
        .then((additionalInfo: AdditionalResults) => {
          setAdditionalResults(additionalInfo.searchResults);
          setHasMoreResult(additionalInfo.hasMoreResults);
          setAdditionalResultNextPage(additionalInfo.nextPage);
        })
        .catch((e: HttpError) => {
          logger.error('Error occured while searching for person credits', e);
          setAdditionalResults([]);
        });
    }
  }, [movie, person, awardType]);

  const resetChoice = () => {
    skipChoice(awardId.toString());
    setNomineeResult(undefined);
    setMovie(undefined);
    setPerson(undefined);
    setAdditionalResults([]);
  };

  const getTitle = () => {
    if (!nomineeResult) {
      return messages.search.favorite;
    }
    return awardType === 'MOVIE' ? nomineeResult.movieTitle : nomineeResult.personName;
  };

  const hasSubheader: boolean = !!(nomineeResult && ((nomineeResult.movieTitle !== nomineeResult.frenchMovieTitle)
    || (awardType === 'CREW')
    || (awardType === 'CAST')));

  return (
    <div className='nominee search'>
      <Card>
        <CardHeader
          title={getTitle()}
          subheader={
            hasSubheader
              ? (
                <div className="nominee-alt-title">({nomineeResult!.frenchMovieTitle})</div>
              )
              : (
                <></>
              )
          }
        />
        <div className="search">
          <CardContent>
            {!nomineeResult
              && <div className="search-nominee">
                {(awardType !== 'MOVIE') && <ToggleButtonGroup
                  value={searchType}
                  exclusive
                  onChange={handleChange}
                >
                  <ToggleButton value="MOVIES" aria-label="MOVIES" className="winner">
                    <Typography variant="subtitle2" component="h5">
                      {messages.search.movies}
                    </Typography>
                  </ToggleButton>
                  <ToggleButton value="PERSONS" aria-label="PERSONS" className="favorites">
                    <Typography variant="subtitle2" component="h5">
                      {messages.search.persons}
                    </Typography>
                  </ToggleButton>
                </ToggleButtonGroup>}
                <Autocomplete
                  id="search"
                  open={open}
                  onOpen={() => {
                    setOpen(true);
                  }}
                  onClose={() => {
                    setOpen(false);
                  }}
                  getOptionKey={(x: SearchResult) => x.id}
                  filterOptions={(x: SearchResult[]) => x}
                  onInputChange={debounce(
                    (_: SyntheticEvent<Element, Event>, value: string) => {
                      search(value);
                    },
                    1000,
                  )}
                  onChange={onAutocompleteChoice}
                  isOptionEqualToValue={(option: SearchResult, value: SearchResult) => option.id === value.id}
                  getOptionLabel={(option: SearchResult) => option.name}
                  options={options}
                  loading={loader.isLoading}
                  renderInput={(params: AutocompleteRenderInputParams) => (
                    <TextField
                      {...params}
                      InputProps={{
                        ...params.InputProps,
                        placeholder: messages.action.search,
                        endAdornment: (
                          <React.Fragment>
                            {loader.isLoading ? <CircularProgress color="inherit" size={20}/> : null}
                            {params.InputProps.endAdornment}
                          </React.Fragment>
                        ),
                      }}
                    />
                  )}
                />
                {(additionalResults && additionalResults.length > 0)
                  && <>
                    <List>
                      {additionalResults
                        .map((additionalResult: SearchResult) => (
                          <ListItem key={additionalResult.id} disablePadding>
                            <ListItemButton onClick={() => onAdditionalResultChoice(additionalResult)}>
                              <ListItemText primary={additionalResult.name}/>
                            </ListItemButton>
                          </ListItem>
                        ))
                      }
                    </List>
                    {
                      hasMoreResult && <Button
                        variant="text"
                        onClick={loadMoreResults}
                      >
                        {messages.action.load_more}
                      </Button>
                    }
                  </>
                }
              </div>
            }
            {
              nomineeResult && (
                <div>
                  <div className="nominee-images">
                    <CardMedia
                      component="img"
                      className={(awardType === 'CAST') ? 'secondary-image' : 'main-image'}
                      alt={nomineeResult.movieTitle}
                      image={nomineeResult.movieMediaUrl}
                    />
                    {
                      (awardType === 'CAST')
                      && <CardMedia
                        component="img"
                        className="main-image"
                        alt={nomineeResult.personName}
                        image={nomineeResult.personMediaUrl}
                      />
                    }
                  </div>
                  <CardActions>
                    <ActionsContainer>
                      <ActionButton actionStyle="primary"
                                    onClick={() => resetChoice()}>
                        {messages.action.delete_choice}
                      </ActionButton>
                      <ActionButton
                        actionStyle="secondary"
                        icon={<FavoriteIcon/>}
                        disabled
                        cssClasses="favorite-selected"
                      >
                        {messages.ceremony.favorite}
                      </ActionButton>
                    </ActionsContainer>
                  </CardActions>
                </div>
              )
            }
          </CardContent>
        </div>
      </Card>
    </div>
  );
}
