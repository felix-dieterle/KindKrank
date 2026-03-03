import React from 'react';
import {
  View,
  Text,
  TouchableOpacity,
  StyleSheet,
  SafeAreaView,
  ScrollView,
} from 'react-native';
import { NativeStackNavigationProp } from '@react-navigation/native-stack';
import { RootStackParamList } from '../../types';
import WizardProgress from '../../components/WizardProgress';

type Props = {
  navigation: NativeStackNavigationProp<RootStackParamList, 'Step1FamilySituation'>;
};

export default function Step1FamilySituation({ navigation }: Props) {
  return (
    <SafeAreaView style={styles.container}>
      <ScrollView contentContainerStyle={styles.content}>
        <WizardProgress currentStep={1} totalSteps={4} />

        <Text style={styles.stepLabel}>Schritt 1 von 4</Text>
        <Text style={styles.title}>Ihre Familiensituation</Text>
        <Text style={styles.description}>
          Damit wir Sie optimal begleiten können, benötigen wir einige Angaben zu Ihrer
          Situation.
        </Text>

        <Text style={styles.questionText}>Wie ist Ihre Situation?</Text>

        <TouchableOpacity
          style={styles.optionCard}
          onPress={() =>
            navigation.navigate('Step2Krankenkasse', { isSingleParent: false })
          }
          accessibilityLabel="Beide Elternteile sind berufstätig"
        >
          <Text style={styles.optionEmoji}>👫</Text>
          <View style={styles.optionTextArea}>
            <Text style={styles.optionTitle}>Beide Elternteile berufstätig</Text>
            <Text style={styles.optionSubtitle}>
              Sie und Ihr Partner / Ihre Partnerin sind beide erwerbstätig
            </Text>
          </View>
        </TouchableOpacity>

        <TouchableOpacity
          style={styles.optionCard}
          onPress={() =>
            navigation.navigate('Step2Krankenkasse', { isSingleParent: true })
          }
          accessibilityLabel="Alleinerziehend"
        >
          <Text style={styles.optionEmoji}>🧑</Text>
          <View style={styles.optionTextArea}>
            <Text style={styles.optionTitle}>Alleinerziehend</Text>
            <Text style={styles.optionSubtitle}>
              Sie erziehen Ihr Kind allein und sind berufstätig
            </Text>
          </View>
        </TouchableOpacity>

        <View style={styles.infoBox}>
          <Text style={styles.infoTitle}>💡 Was ändert sich?</Text>
          <Text style={styles.infoText}>
            <Text style={styles.bold}>Beide Eltern berufstätig:</Text> Jedes Elternteil hat
            Anspruch auf{' '}
            <Text style={styles.bold}>10 Kinderkranktage pro Kind pro Jahr</Text> (max. 25
            Tage gesamt bei mehreren Kindern).
          </Text>
          <Text style={styles.infoText}>
            <Text style={styles.bold}>Alleinerziehend:</Text> Sie haben Anspruch auf{' '}
            <Text style={styles.bold}>20 Kinderkranktage pro Kind pro Jahr</Text> (max. 50
            Tage gesamt).
          </Text>
          <Text style={styles.infoText}>
            Für Kinder unter 12 Jahren. Kinderkrankengeld wird von der Krankenkasse gezahlt.
          </Text>
        </View>
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
  questionText: {
    fontSize: 17,
    fontWeight: '600',
    color: '#333',
    marginBottom: 12,
  },
  optionCard: {
    backgroundColor: '#fff',
    borderRadius: 14,
    padding: 18,
    flexDirection: 'row',
    alignItems: 'center',
    marginBottom: 14,
    elevation: 2,
    shadowColor: '#000',
    shadowOffset: { width: 0, height: 1 },
    shadowOpacity: 0.08,
    shadowRadius: 3,
    borderWidth: 1.5,
    borderColor: 'transparent',
  },
  optionEmoji: {
    fontSize: 36,
    marginRight: 16,
  },
  optionTextArea: {
    flex: 1,
  },
  optionTitle: {
    fontSize: 17,
    fontWeight: 'bold',
    color: '#1A5276',
    marginBottom: 4,
  },
  optionSubtitle: {
    fontSize: 14,
    color: '#666',
    lineHeight: 20,
  },
  infoBox: {
    backgroundColor: '#EBF5FB',
    borderRadius: 12,
    padding: 16,
    marginTop: 8,
    borderLeftWidth: 4,
    borderLeftColor: '#2E86AB',
  },
  infoTitle: {
    fontSize: 15,
    fontWeight: 'bold',
    color: '#1A5276',
    marginBottom: 10,
  },
  infoText: {
    fontSize: 14,
    color: '#444',
    lineHeight: 21,
    marginBottom: 8,
  },
  bold: {
    fontWeight: 'bold',
  },
});
