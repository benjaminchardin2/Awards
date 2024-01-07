import { useLayoutEffect, useState } from 'react';
import { debounce } from '@mui/material';

const useIsMobile = (): boolean => {
  const [isMobile, setIsMobile] = useState(false);

  useLayoutEffect(() => {
    const updateSize = (): void => {
      setIsMobile(window.innerWidth < 1200);
    };

    window.addEventListener('resize', debounce(updateSize, 250));
    updateSize();
    return (): void => window.removeEventListener('resize', updateSize);
  }, []);

  return isMobile;
};

export default useIsMobile;
