import { useState } from 'react';

export type SwipeableIndex = {
  index: number,
  setIndex: React.Dispatch<React.SetStateAction<number>>,
  onPrevious: () => void,
  onNext: () => void,
};
const useSwipeableIndex = (array: unknown[], size?: number) : SwipeableIndex => {
  const [index, setIndex] = useState<number>(0);

  const onPrevious = () => {
    if ((index - 1) >= 0) {
      setIndex(index - 1);
    } else {
      setIndex((size || array.length) - 1);
    }
  };

  const onNext = () => {
    if ((index + 1) < (size || array.length)) {
      setIndex(index + 1);
    } else {
      setIndex(0);
    }
  };

  return {
    index,
    setIndex,
    onPrevious,
    onNext,
  };
};

export default useSwipeableIndex;
