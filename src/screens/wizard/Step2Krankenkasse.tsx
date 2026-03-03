import React from 'react';
import {
  View,
  Text,
  TouchableOpacity,
  StyleSheet,
  SafeAreaView,
  ScrollView,
  Linking,
} from 'react-native';
import { NativeStackNavigationProp } from '@react-navigation/native-stack';
import { RouteProp } from '@react-navigation/native';
import { RootStackParamList, KRANKENKASSEN } from '../../types';
import WizardProgress from '../../components/WizardProgress';

type Props = {
  navigation: NativeStackNavigationProp<RootStackParamList, 'Step2Krankenkasse'>;
  route: RouteProp<RootStackParamList, 'Step2Krankenkasse'>;
};

export default function Step2Krankenkasse({ navigation, route }: Props) {
  const { isSingleParent } = route.params;

  return (
    <SafeAreaView style={styles.container}>
      <ScrollView contentContainerStyle={styles.content}>
        <WizardProgress currentStep={2} totalSteps={4} />

        <Text style={styles.stepLabel}>Schritt 2 von 4</Text>
        <Text style={styles.title}>Ihre Krankenkasse</Text>
        <Text style={styles.description}>
          Wählen Sie Ihre Krankenkasse, damit wir Ihnen den direkten Weg zur Beantragung
          des Kinderkrankengeldes zeigen können.
        </Text>

        {KRANKENKASSEN.map((kk) => (
          <TouchableOpacity
            key={kk.id}
            style={styles.kkCard}
            onPress={() =>
              navigation.navigate('Step3Doctor', {
                isSingleParent,
                krankenkasse: kk.id,
              })
            }
            accessibilityLabel={`Krankenkasse ${kk.name} auswählen`}
          >
            <View style={styles.kkHeader}>
              <Text style={styles.kkShortName}>{kk.shortName}</Text>
              <Text style={styles.kkName}>{kk.name}</Text>
            </View>
            {kk.digitalSubmissionNote ? (
              <Text style={styles.kkNote}>✅ {kk.digitalSubmissionNote}</Text>
            ) : null}
          </TouchableOpacity>
        ))}

        <TouchableOpacity
          style={styles.otherKkCard}
          onPress={() =>
            navigation.navigate('Step3Doctor', {
              isSingleParent,
              krankenkasse: 'other',
            })
          }
          accessibilityLabel="Andere Krankenkasse auswählen"
        >
          <Text style={styles.otherKkText}>🏥  Andere Krankenkasse</Text>
          <Text style={styles.otherKkSub}>
            Allgemeiner Weg – ohne kassenspezifische Shortcuts
          </Text>
        </TouchableOpacity>
      </ScrollView>
    </SafeAreaView>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#E6F4FE',
  },
  content: {
    padding: 20,
    paddingBottom: 40,
  },
  stepLabel: {
    fontSize: 13,
    color: '#888',
    textAlign: 'center',
    marginBottom: 4,
  },
  title: {
    fontSize: 24,
    fontWeight: 'bold',
    color: '#1A5276',
    textAlign: 'center',
    marginBottom: 10,
  },
  description: {
    fontSize: 15,
    color: '#555',
    textAlign: 'center',
    lineHeight: 22,
    marginBottom: 24,
  },
  kkCard: {
    backgroundColor: '#fff',
    borderRadius: 14,
    padding: 16,
    marginBottom: 12,
    elevation: 2,
    shadowColor: '#000',
    shadowOffset: { width: 0, height: 1 },
    shadowOpacity: 0.08,
    shadowRadius: 3,
    borderWidth: 1.5,
    borderColor: '#D6EAF8',
  },
  kkHeader: {
    flexDirection: 'row',
    alignItems: 'center',
    marginBottom: 6,
  },
  kkShortName: {
    fontSize: 18,
    fontWeight: 'bold',
    color: '#2E86AB',
    marginRight: 10,
    minWidth: 60,
  },
  kkName: {
    fontSize: 15,
    color: '#333',
    flex: 1,
  },
  kkNote: {
    fontSize: 13,
    color: '#555',
    lineHeight: 19,
  },
  otherKkCard: {
    backgroundColor: '#F0F0F0',
    borderRadius: 14,
    padding: 16,
    marginTop: 4,
    alignItems: 'center',
  },
  otherKkText: {
    fontSize: 16,
    fontWeight: '600',
    color: '#555',
  },
  otherKkSub: {
    fontSize: 13,
    color: '#888',
    marginTop: 4,
  },
});
