export type RootStackParamList = {
  Disclaimer: undefined;
  Home: undefined;
  Step1FamilySituation: undefined;
  Step2Krankenkasse: { isSingleParent: boolean };
  Step3Doctor: { isSingleParent: boolean; krankenkasse: string };
  Step4EmployerAndInsurance: {
    isSingleParent: boolean;
    krankenkasse: string;
  };
  SpecialCases: undefined;
};

export interface Krankenkasse {
  id: string;
  name: string;
  shortName: string;
  appUrl?: string;
  onlinePortalUrl?: string;
  hotline?: string;
  digitalSubmissionNote?: string;
}

export const KRANKENKASSEN: Krankenkasse[] = [
  {
    id: 'tk',
    name: 'Techniker Krankenkasse',
    shortName: 'TK',
    appUrl: 'tk.de',
    onlinePortalUrl: 'https://www.tk.de/service/app/2055448/kinderkrankengeld.app',
    hotline: '0800 285 85 85',
    digitalSubmissionNote:
      'Die TK ermöglicht die digitale Einreichung über die TK-App oder das Online-Portal.',
  },
  {
    id: 'aok',
    name: 'AOK',
    shortName: 'AOK',
    appUrl: 'aok.de',
    onlinePortalUrl: 'https://www.aok.de/pk/leistungen/kinder/kind-krank/',
    hotline: '0800 0 10 20 20',
    digitalSubmissionNote:
      'Nutzen Sie das AOK Online-Portal oder die AOK App zur Einreichung.',
  },
  {
    id: 'barmer',
    name: 'Barmer',
    shortName: 'Barmer',
    appUrl: 'barmer.de',
    onlinePortalUrl: 'https://www.barmer.de/unsere-leistungen/leistungen-a-z/kinderkrankengeld',
    hotline: '0800 333 1010',
    digitalSubmissionNote:
      'Barmer bietet die Einreichung per App oder Online-Service an.',
  },
  {
    id: 'dak',
    name: 'DAK Gesundheit',
    shortName: 'DAK',
    appUrl: 'dak.de',
    onlinePortalUrl: 'https://www.dak.de/dak/leistungen/kinderkrankengeld/',
    hotline: '040 325 325 325',
    digitalSubmissionNote:
      'DAK Gesundheit bietet einen Online-Antrag für Kinderkrankengeld an.',
  },
  {
    id: 'kkh',
    name: 'KKH Kaufmännische Krankenkasse',
    shortName: 'KKH',
    appUrl: 'kkh.de',
    onlinePortalUrl: 'https://www.kkh.de/leistungen/kinderkrankengeld',
    hotline: '0800 2 55 82 55',
    digitalSubmissionNote:
      'Die KKH ermöglicht die Antragstellung über das Online-Portal.',
  },
];
