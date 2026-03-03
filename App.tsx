import React from 'react';
import { NavigationContainer } from '@react-navigation/native';
import { createNativeStackNavigator } from '@react-navigation/native-stack';
import { StatusBar } from 'expo-status-bar';

import { RootStackParamList } from './src/types';
import DisclaimerScreen from './src/screens/DisclaimerScreen';
import HomeScreen from './src/screens/HomeScreen';
import Step1FamilySituation from './src/screens/wizard/Step1FamilySituation';
import Step2Krankenkasse from './src/screens/wizard/Step2Krankenkasse';
import Step3Doctor from './src/screens/wizard/Step3Doctor';
import Step4EmployerAndInsurance from './src/screens/wizard/Step4EmployerAndInsurance';
import SpecialCasesScreen from './src/screens/SpecialCasesScreen';

const Stack = createNativeStackNavigator<RootStackParamList>();

export default function App() {
  return (
    <NavigationContainer>
      <StatusBar style="auto" />
      <Stack.Navigator
        initialRouteName="Disclaimer"
        screenOptions={{
          headerStyle: { backgroundColor: '#2E86AB' },
          headerTintColor: '#fff',
          headerTitleStyle: { fontWeight: 'bold' },
        }}
      >
        <Stack.Screen
          name="Disclaimer"
          component={DisclaimerScreen}
          options={{ title: 'Rechtlicher Hinweis', headerShown: false }}
        />
        <Stack.Screen
          name="Home"
          component={HomeScreen}
          options={{ title: 'KindKrank', headerShown: false }}
        />
        <Stack.Screen
          name="Step1FamilySituation"
          component={Step1FamilySituation}
          options={{ title: 'Familiensituation' }}
        />
        <Stack.Screen
          name="Step2Krankenkasse"
          component={Step2Krankenkasse}
          options={{ title: 'Krankenkasse wählen' }}
        />
        <Stack.Screen
          name="Step3Doctor"
          component={Step3Doctor}
          options={{ title: 'Arztbesuch' }}
        />
        <Stack.Screen
          name="Step4EmployerAndInsurance"
          component={Step4EmployerAndInsurance}
          options={{ title: 'Arbeitgeber & Kasse' }}
        />
        <Stack.Screen
          name="SpecialCases"
          component={SpecialCasesScreen}
          options={{ title: 'Sonderfälle' }}
        />
      </Stack.Navigator>
    </NavigationContainer>
  );
}
