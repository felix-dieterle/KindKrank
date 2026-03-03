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
import { RouteProp } from '@react-navigation/native';
import { RootStackParamList } from '../../types';
import WizardProgress from '../../components/WizardProgress';

type Props = {
  navigation: NativeStackNavigationProp<RootStackParamList, 'Step3Doctor'>;
  route: RouteProp<RootStackParamList, 'Step3Doctor'>;
};

export default function Step3Doctor({ navigation, route }: Props) {
  const { isSingleParent, krankenkasse } = route.params;

  return (
    <SafeAreaView style={styles.container}>
      <ScrollView contentContainerStyle={styles.content}>
        <WizardProgress currentStep={3} totalSteps={4} />

        <Text style={styles.stepLabel}>Schritt 3 von 4</Text>
        <Text style={styles.title}>Zum Arzt</Text>
        <Text style={styles.description}>
          Der erste Schritt: Ihr Kind muss von einem Arzt untersucht werden und Sie
          benötigen eine offizielle Bescheinigung.
        </Text>

        <View style={styles.actionCard}>
          <Text style={styles.actionNumber}>1</Text>
          <View style={styles.actionContent}>
            <Text style={styles.actionTitle}>🩺 Kinderarzt aufsuchen</Text>
            <Text style={styles.actionText}>
              Bringen Sie Ihr Kind zum Kinderarzt (Pädiater) oder Hausarzt. Schildern Sie
              die Erkrankung.
            </Text>
          </View>
        </View>

        <View style={styles.actionCard}>
          <Text style={styles.actionNumber}>2</Text>
          <View style={styles.actionContent}>
            <Text style={styles.actionTitle}>📄 Kinderkrankenschein anfordern</Text>
            <Text style={styles.actionText}>
              Bitten Sie explizit um eine{' '}
              <Text style={styles.bold}>„ärztliche Bescheinigung zur Vorlage bei der Krankenkasse"</Text>{' '}
              (Kinderkrankenschein / Muster 21). Diese ist{' '}
              <Text style={styles.bold}>unbedingt notwendig</Text>, um Kinderkrankengeld zu
              beantragen.
            </Text>
          </View>
        </View>

        <View style={styles.actionCard}>
          <Text style={styles.actionNumber}>3</Text>
          <View style={styles.actionContent}>
            <Text style={styles.actionTitle}>📋 Was der Arzt bescheinigt</Text>
            <Text style={styles.actionText}>
              Der Arzt bescheinigt, dass Ihr Kind krank ist und Betreuung benötigt sowie
              die voraussichtliche Dauer der Erkrankung. Sie können für diese Zeit zu Hause
              bleiben.
            </Text>
          </View>
        </View>

        <View style={styles.tipBox}>
          <Text style={styles.tipTitle}>💡 Wichtige Tipps</Text>
          <Text style={styles.tipText}>
            • Beantragen Sie den Kinderkrankenschein{' '}
            <Text style={styles.bold}>gleich beim ersten Arztbesuch</Text> – nachträglich
            ist es schwieriger.
          </Text>
          <Text style={styles.tipText}>
            • Falls Ihr Kind ins Krankenhaus muss, fragen Sie dort ebenfalls nach einer
            entsprechenden Bescheinigung.
          </Text>
          <Text style={styles.tipText}>
            • Digitale Übermittlung: Manche Arztpraxen können den Schein direkt an Ihre
            Krankenkasse senden.
          </Text>
        </View>

        <TouchableOpacity
          style={styles.nextButton}
          onPress={() =>
            navigation.navigate('Step4EmployerAndInsurance', {
              isSingleParent,
              krankenkasse,
            })
          }
          accessibilityLabel="Weiter zu Schritt 4: Arbeitgeber und Krankenkasse"
        >
          <Text style={styles.nextButtonText}>
            Ich habe den Kinderkrankenschein →
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
  actionCard: {
    backgroundColor: '#fff',
    borderRadius: 14,
    padding: 16,
    flexDirection: 'row',
    alignItems: 'flex-start',
    marginBottom: 12,
    elevation: 2,
    shadowColor: '#000',
    shadowOffset: { width: 0, height: 1 },
    shadowOpacity: 0.08,
    shadowRadius: 3,
  },
  actionNumber: {
    width: 32,
    height: 32,
    borderRadius: 16,
    backgroundColor: '#2E86AB',
    color: '#fff',
    fontSize: 16,
    fontWeight: 'bold',
    textAlign: 'center',
    lineHeight: 32,
    marginRight: 14,
    marginTop: 2,
    overflow: 'hidden',
  },
  actionContent: {
    flex: 1,
  },
  actionTitle: {
    fontSize: 16,
    fontWeight: 'bold',
    color: '#1A5276',
    marginBottom: 6,
  },
  actionText: {
    fontSize: 14,
    color: '#444',
    lineHeight: 21,
  },
  bold: {
    fontWeight: 'bold',
  },
  tipBox: {
    backgroundColor: '#EBF5FB',
    borderRadius: 12,
    padding: 16,
    marginTop: 8,
    marginBottom: 24,
    borderLeftWidth: 4,
    borderLeftColor: '#F39C12',
  },
  tipTitle: {
    fontSize: 15,
    fontWeight: 'bold',
    color: '#1A5276',
    marginBottom: 10,
  },
  tipText: {
    fontSize: 14,
    color: '#444',
    lineHeight: 21,
    marginBottom: 6,
  },
  nextButton: {
    backgroundColor: '#2E86AB',
    borderRadius: 12,
    paddingVertical: 16,
    alignItems: 'center',
  },
  nextButtonText: {
    color: '#fff',
    fontSize: 16,
    fontWeight: 'bold',
  },
});
